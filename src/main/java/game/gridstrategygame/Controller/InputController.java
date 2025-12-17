package game.gridstrategygame.Controller;

import game.gridstrategygame.Model.*;
import game.gridstrategygame.View.EffectType;

import java.util.ArrayList;
import java.util.Arrays;

public class InputController {
    // fields
    private ArrayList<Selection> selectionBuffer = new ArrayList<>(); // index 1: select etity, index to is select location
    private ArrayList<Entity> allyRoster;
    private GameController gameController;
    private ArrayList<int[]> clearBuffer = new ArrayList<>(); // positons to clear (valid turn canvas)
    private ArrayList<int[]> clearBufferCooldown = new ArrayList<>(); // positons to clear (valid turn canvas)

    public InputController(GameController gameController, ArrayList<Entity> allyRoster) {
        this.gameController = gameController;
        this.allyRoster = allyRoster;
    }

    // check selection (must be ally to go into position one)
    public boolean checkSelection(Selection selection, EntityMap em) {
        // check if in bounds
        int[] pos = selection.pos;
        if (!em.isInBound(pos)) return false;

        Entity entitySelected = selection.entity;

        if (selectionBuffer.size() == 0) {
            // null check first
            if (entitySelected == null) {
                return false;
            }

            // selection must be ally
            if (!(entitySelected.getAllegiance() == Allegiance.ALLY)) {
                return false;
            }

            // check if on turn cooldown (wait until next turn)
            if (entitySelected.getTurnState() == TurnState.TURN_COOLDOWN) return false;

            // add to buffer
            selectionBuffer.add(selection);

            // based on turn state -> show valid moves
            if (entitySelected.getTurnState() == TurnState.MOVE) {
                ArrayList<int[]> validTurns = getValidMove(entitySelected, em);
                clearBuffer = validTurns;
                gameController.showValidTurns(EffectType.VALID_MOVE, validTurns);
            }
            // if attacking -> show valid attacks
            if (entitySelected.getTurnState() == TurnState.ATTACK) {
                ArrayList<int[]> validTurns = getValidAttack(entitySelected, em);
                clearBuffer = validTurns;
                gameController.showValidTurns(EffectType.VALID_ATTACK, validTurns);
            }
        }

        else if (selectionBuffer.size() == 1) {
            // clear valid turns
            gameController.clearValidTurns(clearBuffer);

            // check turn state of entity (at first index in buffer) (previous selection)
            if (selectionBuffer.get(0).entity.getTurnState() == TurnState.MOVE) {
                // get valid positions
                ArrayList<int[]> validPos = getValidMove(selectionBuffer.get(0).entity, em);

                // if move -> selection must be valid
                boolean isValisPos = false;
                for (int[] vPos : validPos) {
                    // if valid -> do seletion
                    if (Arrays.equals(pos, vPos)) {
                        isValisPos = true;
                        selectionBuffer.add(selection);
                    }
                }

                // if not valid -> clear buffer
                if (!isValisPos) {
                    // clear buffer, return false
                    selectionBuffer.clear();
                    return false;
                }
            }
            if (selectionBuffer.get(0).entity.getTurnState() == TurnState.ATTACK) {
                // get valid attacks
                ArrayList<int[]> validPos = getValidAttack(selectionBuffer.get(0).entity, em);

                // if attack -> selection must be valid
                boolean isValisPos = false;
                for (int[] vPos : validPos) {
                    // if valid -> do seletion
                    if (Arrays.equals(pos, vPos)) {
                        isValisPos = true;
                        selectionBuffer.add(selection);
                    }
                }

                // if not valid -> clear buffer
                if (!isValisPos) {
                    // clear buffer, return false
                    selectionBuffer.clear();
                    return false;
                }

            }
        }

        // if buffer is full -> do action
        if (selectionBuffer.size() == 2) {
            completeTurn(em);
        }

        return true;
    }

    // complete turn
    private void completeTurn(EntityMap em) {
        // get entity doing turn
        Entity entityTurn = selectionBuffer.get(0).entity;

        // get action
        TurnState turnState = entityTurn.getTurnState();

        // get location of action
        int[] location = selectionBuffer.get(1).pos;

        if (turnState == TurnState.MOVE) {
            // move entity
            em.moveEntity(entityTurn, location);
            // update turn state
            entityTurn.updateTurnState();
        }

        else if(turnState == TurnState.ATTACK) {
            // get action type
            EffectType effect = EffectType.WHITE_SLASH_ATTACK;

            // get location
            ArrayList<int[]> locations = new ArrayList<>();
            locations.add(selectionBuffer.get(1).pos);

            // null check -> if null miss
            if (em.getEntityAtPos(selectionBuffer.get(1).pos) == null) {
                ActionsController.miss(effect, locations, gameController);
            }
            else {
                ActionsController.attack(
                        effect,
                        selectionBuffer.get(0).entity,
                        locations,
                        selectionBuffer.get(1).entity,
                        em,
                        gameController);
            }

            // update turn state
            entityTurn.updateTurnState();

            // draw cooldown effect
            ArrayList<int[]> allyPosWrapper = new ArrayList<>();
            allyPosWrapper.add(selectionBuffer.get(0).pos);
            clearBufferCooldown.add(selectionBuffer.get(0).pos);
            gameController.showValidTurns(EffectType.TURN_COOLDOWN, allyPosWrapper);
        }

        // clear buffer
        selectionBuffer.clear();

        // check if all ally turns have been taken
        if (checkTurnEnd()) {
            // set all turn states to move
            resetTurnStates();

            // do computer turn
        }
    }

    // when all allies have taken their turn -> reset turn states, and do enemy turns
    private boolean checkTurnEnd() {
        // if all turn states are on cooldown (from roster) -> set all back to move
        for (Entity e : allyRoster) {
            if (e.getTurnState() != TurnState.TURN_COOLDOWN) {
                return false;
            }
        }
        return true;
    }
    // reset all ally turn state to move, clear all cooldown markers
    public void resetTurnStates() {
        for (Entity e : allyRoster) {
            e.setTurnState(TurnState.MOVE);
            gameController.clearValidTurns(clearBufferCooldown);
            clearBufferCooldown.clear();
        }
    }

    // get list of valid tiles to move to for given entity
    public ArrayList<int[]> getValidMove(Entity e, EntityMap em) {
        ArrayList<int[]> validTilesList = new ArrayList<>();

        // get entity location
        int[] eLocation = e.getPosition();

        // get entity movement type and distance
        MovementType movementType = e.getMovementType();
        int movementDistance = e.getMovementDistance();

        // calculate valid squares
        if (movementType == MovementType.ORTHOGONAL) {
            int[][] orthoNeighours = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
            // for each position leading up to max movement distance, you can move to
            for (int j = 1; j <= movementDistance; j++) {
                for (int[] neighbour : orthoNeighours) {
                    // multiply by movement distance
                    int finalJ = j;
                    Arrays.setAll(neighbour, i -> neighbour[i] * finalJ);
                    int[] next = {neighbour[0] + eLocation[0], neighbour[1] + eLocation[1]};
                    validTilesList.add(next);
                }
            }
        }
        else if (movementType == MovementType.DIAGONAL) {
            int[][] diagoNeighbours = {{-1, -1}, {1, -1}, {1, 1}, {-1, 1}};
            // for each position leading up to max movement distance, you can move to
            for (int j = 1; j <= movementDistance; j++) {
                for (int[] neighbour : diagoNeighbours) {
                    // multiply by movement distance
                    int finalJ = j;
                    Arrays.setAll(neighbour, i -> neighbour[i] * finalJ);
                    int[] next = {neighbour[0] + eLocation[0], neighbour[1] + eLocation[1]};
                    validTilesList.add(next);
                }
            }
        }

        // remove none valid positions
        ArrayList<int[]> toBeRemoved = new ArrayList<>();
        for (int[] pos : validTilesList) {
            // remove all out of bounds
            if (!em.isInBound(pos)) toBeRemoved.add(pos);

                // remove all not walkable
            else if (!em.isWalkable(pos)) toBeRemoved.add(pos);
        }

        for (int[] posRemove : toBeRemoved) {
            validTilesList.remove(posRemove);
        }

        return validTilesList;
    }
    public ArrayList<int[]> getValidAttack(Entity e, EntityMap em) {
        ArrayList<int[]> validTilesList = new ArrayList<>();

        // get entity location
        int[] eLocation = e.getPosition();

        // get entity attack type and distance
        AttackType attackType = e.getAttackType();
        int attackDistance = e.getAttackDistance();

        // calculate valid squares
        if (attackType== AttackType.ORTHOGONAL) {
            int[][] orthoNeighours = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
            for (int[] neighbour : orthoNeighours) {
                // multiply by movement distance
                Arrays.setAll(neighbour, i -> neighbour[i] * attackDistance);

                int[] next = {neighbour[0] + eLocation[0], neighbour[1] + eLocation[1]};

                validTilesList.add(next);
            }
        }
        else if (attackType == AttackType.DIAGONAL) {
            int[][] diagoNeighbours = {{-1, -1}, {1, -1}, {1, 1}, {-1, 1}};
            for (int[] neighbour : diagoNeighbours) {
                // multiply by movement distance
                Arrays.setAll(neighbour, i -> neighbour[i] * attackDistance);

                int[] next = {neighbour[0] + eLocation[0], neighbour[1] + eLocation[1]};

                validTilesList.add(next);
            }
        }

        // remove none valid positions
        ArrayList<int[]> toBeRemoved = new ArrayList<>();
        for (int[] pos : validTilesList) {
            // remove all out of bounds
            if (!em.isInBound(pos)) toBeRemoved.add(pos);

            // remove all that are impassable
            else if ((em.getEntityAtPos(pos) instanceof IMPASSABLE)) toBeRemoved.add(pos);
        }

        for (int[] posRemove : toBeRemoved) {
            validTilesList.remove(posRemove);
        }

        return validTilesList;
    }




}

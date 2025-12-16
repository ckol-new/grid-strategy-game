package game.gridstrategygame.Controller;

import game.gridstrategygame.Model.*;

import java.util.ArrayList;
import java.util.Arrays;

public class InputController {
    // fields
    ArrayList<Selection> selectionBuffer = new ArrayList<>(); // index 1: select etity, index to is select location

    public InputController() {
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

            // add to buffer
            selectionBuffer.add(selection);


        }

        else if (selectionBuffer.size() == 1) {
            // check turn state of entity (at first index in buffer) (previous selection)
            if (selectionBuffer.get(0).entity.getTurnState() == TurnState.MOVE) {
                // get valid positions
                ArrayList<int[]> validPos = getValidTilesMove(selectionBuffer.get(0).entity, em);

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
            //TODO add logic for attacks
        }

        // if buffer is full -> do action
        if (selectionBuffer.size() == 2) {
            completeTurn(em);
        }
        // show user valid turns
        else if (selectionBuffer.size() == 1) {

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

            //TODO update entity turn state (for now don't)
            // DEBUG
            // entityTurn.updateTurnState();
        }



        // clear buffer
        selectionBuffer.clear();

    }

    // get list of valid tiles to move to for given entity
    public ArrayList<int[]> getValidTilesMove(Entity e, EntityMap em) {
        ArrayList<int[]> validTilesList = new ArrayList<>();

        // get entity location
        int[] eLocation = e.getPosition();

        // get entity movement type and distance
        MovementType movementType = e.getMovementType();
        int movementDistance = e.getMovementDistance();

        // calculate valid squares
        if (movementType == MovementType.ORTHOGONAL) {
            int[][] orthoNeighours = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
            for (int[] neighbour : orthoNeighours) {
                // multiply by movement distance
                Arrays.setAll(neighbour, i -> neighbour[i] * movementDistance);

                int[] next = {neighbour[0] + eLocation[0], neighbour[1] + eLocation[1]};

                validTilesList.add(next);
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




}

package game.gridstrategygame.Controller;

import game.gridstrategygame.Model.Allegiance;
import game.gridstrategygame.Model.Entity;
import game.gridstrategygame.Model.EntityMap;
import game.gridstrategygame.Model.TurnState;

import java.util.ArrayList;

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
                // if move -> selection must be walkable
                if (!em.isWalkable(pos)) {
                    // clear buffer, return false
                    selectionBuffer.clear();
                    return false;
                }

                // otherwise, add to buffer
                selectionBuffer.add(selection);
            }
            //TODO add logic for attacks
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

            //TODO update entity turn state (for now don't)
            // DEBUG
            // entityTurn.updateTurnState();
        }

        // clear buffer
        selectionBuffer.clear();

    }


}

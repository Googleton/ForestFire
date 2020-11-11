package com.hv.ff;

import com.hv.Main;

import java.util.List;
import java.util.Set;

public class Tree {

    private TreeState state;
    private Forest forest;
    private int posX;
    private int posY;

    public Tree(Forest forest, int x, int y) {
        state = TreeState.Normal;
        this.forest = forest;
        this.posX = x;
        this.posY = y;
    }

    public void Tick(Set<Tree> treesToBurn) {
        if (state == TreeState.Burning) {
            state = TreeState.Burnt;
            IgniteNeighbors(treesToBurn);
        }
    }

    private void IgniteNeighbors(Set<Tree> treesToBurn) {
        IgniteNeighbor(posX + 1, posY, treesToBurn);
        IgniteNeighbor(posX - 1, posY, treesToBurn);
        IgniteNeighbor(posX, posY + 1, treesToBurn);
        IgniteNeighbor(posX, posY - 1, treesToBurn);
    }

    private void IgniteNeighbor(int x, int y, Set<Tree> treesToBurn) {
        if (Main.Config.SpreadChance > Math.random()) {
            Tree tree = forest.GetTree(x, y);
            if (tree != null && tree.getState() == TreeState.Normal)
                treesToBurn.add(tree);
        }
    }

    public TreeState getState() {
        return state;
    }

    public void setState(TreeState newState)
    {
        state = newState;
    }
}

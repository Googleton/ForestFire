package com.hv.ff;

import java.util.HashSet;
import java.util.Set;

public class Forest {

    private Tree[][] trees;
    private int sizeX;
    private int sizeY;

    public Forest(int width, int height) {
        sizeX = width;
        sizeY = height;

        trees = new Tree[sizeY][sizeX];

        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                trees[i][j] = new Tree(this, j, i);
            }
        }
    }

    public void Ignite(int x, int y) {
        if (x < sizeX && x >= 0
            && y < sizeY && y >= 0)
        {
            Tree tree = trees[y][x];
            if (tree.getState() == TreeState.Normal)
                tree.setState(TreeState.Burning);
        }
    }

    public void Tick() {
        Set<Tree> treesToBurn = new HashSet<>();
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                trees[i][j].Tick(treesToBurn);
            }
        }

        for (Tree tree: treesToBurn) {
            tree.setState(TreeState.Burning);
        }
    }

    public boolean HasBurningTrees() {
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                if (trees[i][j].getState() == TreeState.Burning)
                    return true;
            }
        }

        return false;
    }

    public String DrawForest() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                switch (trees[i][j].getState()) {
                    case Normal:
                        builder.append("\uD83C\uDF33");
                        break;
                    case Burning:
                        builder.append("\uD83D\uDD25");
                        break;
                    case Burnt:
                        builder.append("\uD83D\uDCA5");
                        break;
                }
            }

            builder.append("\n");
        }

        return builder.toString();
    }

    public Tree GetTree(int x, int y) {
        if (x < sizeX && x >= 0 && y < sizeY && y >= 0)
            return trees[y][x];
        return null;
    }
}

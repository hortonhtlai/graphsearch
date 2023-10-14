package Assn4;

import java.util.Arrays;

public class SLSVariant {
    private int[][] initPopulation = {{1, 1, 1, 1, 1, 1, 1, 1},
                                      {2, 2, 2, 2, 2, 2, 2, 2},
                                      {3, 3, 3, 3, 3, 3, 3, 3},
                                      {4, 4, 4, 4, 4, 4, 4, 4},
                                      {1, 2, 3, 4, 1, 2, 3, 4},
                                      {4, 3, 2, 1, 4, 3, 2, 1},
                                      {1, 2, 1, 2, 1, 2, 1, 2},
                                      {3, 4, 3, 4, 3, 4, 3, 4}};
    private int generationNum;
    private int[] fitnessScores;
    private double[] parentLikelihood;
    private int[][] newGeneration;

    private void fillFitnessScore() {
        for (int i = 0; i < initPopulation.length; i++) {
            int A = initPopulation[i][0];
            int B = initPopulation[i][1];
            int C = initPopulation[i][2];
            int D = initPopulation[i][3];
            int E = initPopulation[i][4];
            int F = initPopulation[i][5];
            int G = initPopulation[i][6];
            int H = initPopulation[i][7];
            int fitnessScore = 0;
            if (A > G) fitnessScore++;
            if (A <= H) fitnessScore++;
            if (Math.abs(F - B) == 1) fitnessScore++;
            if (G < H) fitnessScore++;
            if (Math.abs(G - C) == 1) fitnessScore++;
            if (Math.abs(H - C) % 2 == 0) fitnessScore++;
            if (H != D) fitnessScore++;
            if (D >= G) fitnessScore++;
            if (D != C) fitnessScore++;
            if (E != C) fitnessScore++;
            if (E < D - 1) fitnessScore++;
            if (E != H - 2) fitnessScore++;
            if (G != F) fitnessScore++;
            if (H != F) fitnessScore++;
            if (C != F) fitnessScore++;
            if (D != F - 1) fitnessScore++;
            if (Math.abs(E - F) % 2 == 1) fitnessScore++;
            fitnessScores[i] = fitnessScore;
        }
    }

    private void fillParentLikelihood() {
        int fitnessSum = 0;
        for (int fitnessScore : fitnessScores) {
            fitnessSum += fitnessScore;
        }
        for (int i = 0; i < fitnessScores.length; i++) {
            if (fitnessSum == 0) {
                parentLikelihood[i] = 1.0 / fitnessScores.length;
            } else {
                parentLikelihood[i] = (double) fitnessScores[i] / fitnessSum;
            }
        }
    }

    private int sampleFromDistribution(double[] probability) {
        double randomNum = Math.random();
        for (int outcome = 0; outcome < probability.length - 1; outcome++) {
            if (randomNum < probability[outcome]) return outcome;
            randomNum -= probability[outcome];
        }
        return probability.length - 1;
    }

    private void pairSelection(int pairNum) {
        int parent1 = sampleFromDistribution(parentLikelihood);
        int parent2 = parent1;
        while (parent2 == parent1) {
            parent2 = sampleFromDistribution(parentLikelihood);
        }
        for (int i = 0; i < newGeneration[0].length; i++) {
            newGeneration[2*pairNum][i] = initPopulation[parent1][i];
            newGeneration[2*pairNum + 1][i] = initPopulation[parent2][i];
        }

        System.out.print("Selection: ");
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < newGeneration[0].length; j++) {
                System.out.print(" " + newGeneration[2*pairNum + i][j]);
            }
            System.out.print("    ");
        }
        System.out.println();
    }

    private void pairCrossover(int pairNum) {
        double[] crossoverPoints = new double[initPopulation.length - 1];
        Arrays.fill(crossoverPoints, 1.0 / crossoverPoints.length);
        int crossoverPoint = sampleFromDistribution(crossoverPoints);
        for (int i = crossoverPoint + 1; i < newGeneration[0].length; i++) {
            int temp = newGeneration[2*pairNum][i];
            newGeneration[2*pairNum][i] = newGeneration[2*pairNum + 1][i];
            newGeneration[2*pairNum + 1][i] = temp;
        }

        System.out.print("Crossover: ");
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < newGeneration[0].length; j++) {
                if (j - 1 == crossoverPoint) {
                    System.out.print("|" + newGeneration[2*pairNum + i][j]);
                } else {
                    System.out.print(" " + newGeneration[2*pairNum + i][j]);
                }
            }
            System.out.print("    ");
        }
        System.out.println();
    }

    private void pairMutation(int pairNum) {
        System.out.print("Mutation:  ");
        for (int i = 0; i < 2; i++) {
            int mutationPoint = -10;
            if (Math.random() < 0.3) {
                double[] mutationPoints = new double[initPopulation.length];
                Arrays.fill(mutationPoints, 1.0 / mutationPoints.length);
                mutationPoint = sampleFromDistribution(mutationPoints);
                double[] mutationValues = new double[initPopulation.length];
                Arrays.fill(mutationValues, 1.0 / 4);
                int mutationValue = newGeneration[2*pairNum + i]
                        [mutationPoint];
                while (mutationValue == newGeneration[2*pairNum + i]
                        [mutationPoint]) {
                    mutationValue = sampleFromDistribution(mutationValues) + 1;
                }
                newGeneration[2*pairNum + i][mutationPoint] = mutationValue;
            }
            for (int j = 0; j < newGeneration[0].length; j++) {
                if (j == mutationPoint) {
                    System.out.print("(" + newGeneration[2*pairNum + i][j] + ")");
                } else if (j-1 == mutationPoint) {
                    System.out.print(newGeneration[2*pairNum + i][j]);
                } else {
                    System.out.print(" " + newGeneration[2*pairNum + i][j]);
                }
            }
            System.out.print("    ");
        }
        System.out.println();
    }

    public void geneticAlg() {
        generationNum = 0;
        fitnessScores = new int[initPopulation.length];
        parentLikelihood = new double[initPopulation.length];
        fillFitnessScore();
        fillParentLikelihood();
        printPopulationInfo();
        while (generationNum < 5) {
            newGeneration = new int[initPopulation.length]
                    [initPopulation[0].length];
            for (int i = 0; i < initPopulation.length / 2; i++) {
                System.out.println("Pair " + (i + 1) + ":");
                pairSelection(i);
                pairCrossover(i);
                pairMutation(i);
            }
            initPopulation = newGeneration;
            generationNum++;
            fillFitnessScore();
            fillParentLikelihood();
            printPopulationInfo();
        }
    }

    private void printPopulationInfo() {
        System.out.println("\nGeneration " + generationNum + ":");
        for (int i = 0; i < initPopulation.length; i++) {
            System.out.print("State " + (i + 1) + ":");
            for (int j = 0; j < initPopulation[0].length; j++) {
                System.out.print(" " + initPopulation[i][j]);
            }
            System.out.println("   Fitness score: " + fitnessScores[i]
                    + "    Parent likelihood: " + parentLikelihood[i]);
        }
    }
}

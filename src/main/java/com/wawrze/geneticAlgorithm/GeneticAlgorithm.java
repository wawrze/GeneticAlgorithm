package com.wawrze.geneticAlgorithm;

import java.util.*;

class GeneticAlgorithm {

        static final Random GENERATOR = new Random();
        static String GENES = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ 1234567890,.-;:_!\\\"#%&/()=?@${[]}";

        private final String target;
        private final int population;
        private final int maxGenerationsCount;
        private final double crossFactor;
        private final double mutateFactor;

        private final List<List<Individual>> generations;
        private int firstGenerationOfBestFit = 1;

        GeneticAlgorithm(String target, int population, int maxGenerationsCount, double crossFactor, double mutateFactor) {
            this.target = target;
            this.population = population;
            this.maxGenerationsCount = maxGenerationsCount;
            this.crossFactor = crossFactor;
            this.mutateFactor = mutateFactor;
            this.generations = new ArrayList<>();
        }

        void start() {
            Individual bestFit = setFirstGeneration();
            printGeneration(1, bestFit);
            while (
                    (generations.size() < maxGenerationsCount || maxGenerationsCount == -1)
                            && bestFit.getFitness() < target.length()
            ) {
                Individual bestFitInGeneration = makeNewGeneration();
                printGeneration(generations.size(), bestFitInGeneration);
                if (bestFitInGeneration.compareTo(bestFit) > 0) {
                    bestFit = bestFitInGeneration;
                    firstGenerationOfBestFit = generations.size();
                }
            }
            System.out.println("--------------------------------------");
            System.out.println("> BEST FIT FOUND IN GENERATION " + firstGenerationOfBestFit);
            System.out.println("> " + bestFit);
            System.out.println("> FITNESS = " + bestFit.getFitness() + "/" + target.length());
            System.out.println("--------------------------------------\n");
        }

        private Individual makeNewGeneration() {
            List<Individual> newGeneration = new ArrayList<>();
            Individual bestFitInGeneration = null;
            List<Individual> sortedLastGeneration = sortGeneration(generations.size() - 1);

            int generationFitnessSum = sortedLastGeneration.stream()
                    .map(Individual::getFitness)
                    .reduce(Integer::sum)
                    .orElse(0);
            int[] oldGenerationIndividualsFitness = new int[population];
            int partialSum = 0;
            for (int i = 0; i < population; i++) {
                if (generationFitnessSum > 0) {
                    int fitness = 100 * sortedLastGeneration.get(i).getFitness() / generationFitnessSum;
                    oldGenerationIndividualsFitness[i] = partialSum + fitness;
                    partialSum += fitness;
                } else {
                    oldGenerationIndividualsFitness[i] = 100 * (i + 1) / population;
                }
            }
            System.out.println();
            for (int i = 0; i < population; i++) {
                int random = GENERATOR.nextInt(100);
                int newIndividualNumber = 0;
                for (int j = 0; j < population; j++) {
                    if (random < oldGenerationIndividualsFitness[j]) {
                        break;
                    } else {
                        newIndividualNumber = j;
                    }
                }
                newGeneration.add(sortedLastGeneration.get(newIndividualNumber));
                if (i % 2 == 1 && GENERATOR.nextDouble() < crossFactor) {
                    Individual child1 = newGeneration.get(i - 1).cross(newGeneration.get(i)).mutate();
                    Individual child2 = newGeneration.get(i).cross(newGeneration.get(i - 1)).mutate();
                    newGeneration.set(i - 1, child1);
                    newGeneration.set(i, child2);
                }
                if (bestFitInGeneration == null || newGeneration.get(i).compareTo(bestFitInGeneration) > 0) {
                    bestFitInGeneration = newGeneration.get(i);
                }
            }
            generations.add(new ArrayList<>(newGeneration));
            return bestFitInGeneration;
        }

        private List<Individual> sortGeneration(int generationNumber) {
            List<Individual> list = new ArrayList<>(generations.get(generationNumber));
            list.sort(Individual::compareTo);
            return list;
        }

    private Individual setFirstGeneration() {
        Set<Individual> firstGeneration = new HashSet<>();
        Individual bestFitInGeneration = null;
        for (int i = 0; i < population; i++) {
            Individual individual = new Individual(target, mutateFactor);
            if (bestFitInGeneration == null || individual.compareTo(bestFitInGeneration) > 0) {
                bestFitInGeneration = individual;
            }
            firstGeneration.add(individual);
        }
        generations.add(new ArrayList<>(firstGeneration));
        return bestFitInGeneration;
    }

    private void printGeneration(int generationNumber, Individual bestFitInGeneration) {
        System.out.println("--------------------------------------");
        System.out.println("> GENERATION " + generationNumber);
        System.out.println("--------------------------------------");
        generations.get(generationNumber - 1)
                .forEach(individual -> System.out.println("> " + individual));
        System.out.println("--------------------------------------");
        System.out.println("> BEST FIT:");
        System.out.println("> " + bestFitInGeneration);
        System.out.println("> FITNESS = " + bestFitInGeneration.getFitness() + "/" + target.length());
        System.out.println("--------------------------------------\n");
    }

}

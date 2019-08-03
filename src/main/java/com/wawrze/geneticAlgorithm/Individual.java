package com.wawrze.geneticAlgorithm;

class Individual implements Comparable<Individual> {

    private final String target;
    private final int chromosomeLength;
    private final double mutateFactor;

    private String chromosome;
    private int fitness;

    Individual(String target, double mutateFactor) {
        this.chromosomeLength = target.length();
        this.target = target;
        StringBuilder chromosome = new StringBuilder();
        for (int i = 0; i < chromosomeLength; i++) {
            chromosome.append(randGene());
        }
        this.chromosome = chromosome.toString();
        this.mutateFactor = mutateFactor;
        this.fitness = calculateFitness(chromosome.toString());
    }

    private Individual(String chromosome, String target, double mutateFactor) {
        this.chromosome = chromosome;
        this.fitness = calculateFitness(chromosome);
        this.target = target;
        this.chromosomeLength = target.length();
        this.mutateFactor = mutateFactor;
    }

    Individual cross(Individual pair) {
        StringBuilder childChromosome = new StringBuilder();
        int i = 0;
        while (i < chromosomeLength / 2) {
            childChromosome.append(this.chromosome.charAt(i));
            i++;
        }
        while (i < chromosomeLength) {
            childChromosome.append(pair.chromosome.charAt(i));
            i++;
        }
        return new Individual(childChromosome.toString(), target, mutateFactor);
    }

    Individual mutate() {
        StringBuilder newChromosome = new StringBuilder();
        for (int i = 0; i < chromosomeLength; i++) {
            if (GeneticAlgorithm.GENERATOR.nextDouble() < mutateFactor) {
                newChromosome.append(randGene());
            } else {
                newChromosome.append(chromosome.charAt(i));
            }
        }
        this.chromosome = newChromosome.toString();
        this.fitness = calculateFitness(newChromosome.toString());
        return this;
    }

    private int calculateFitness(String chromosome) {
        int fitness = 0;
        for (int i = 0; i < chromosomeLength; i++) {
            if (chromosome.charAt(i) == target.charAt(i)) {
                fitness++;
            }
        }
        return fitness;
    }

    private char randGene() {
        return GeneticAlgorithm.GENES.charAt(GeneticAlgorithm.GENERATOR.nextInt(GeneticAlgorithm.GENES.length()));
    }

    int getFitness() {
        return fitness;
    }

    @Override
    public String toString() {
        return chromosome;
    }

    @Override
    public int compareTo(Individual o) {
        return this.fitness - o.fitness;
    }

}
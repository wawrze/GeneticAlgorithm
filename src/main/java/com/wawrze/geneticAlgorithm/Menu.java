package com.wawrze.geneticAlgorithm;

import java.util.Locale;
import java.util.Scanner;

class Menu {

    private final Scanner scanner = new Scanner(System.in).useLocale(Locale.US);

    private String target = "Mateusz Wawreszuk";
    private int population = 100;
    private int maxGenerationsCount = -1;
    private double crossFactor = 0.8;
    private double mutateFactor = 0.1;

    void start() {
        String o;
        do {
            printMenu();
            o = scanner.nextLine();
            option(o);
        } while (!o.equals("x"));
    }

    private void printMenu() {
        this.cls();
        System.out.println("\t╔═════════════════════════╗");
        System.out.println("\t║           MENU          ║");
        System.out.println("\t╠═════════════════════════╣");
        System.out.println("\t║ (s) Start algorithm     ║");
        System.out.println("\t╠═════════════════════════╣");
        System.out.println("\t║ (x) Exit                ║");
        System.out.println("\t╠═════════════════════════╣");
        System.out.println("\t║            or           ║");
        System.out.println("\t╠═════════════════════════╣");
        System.out.println("\t║ (e) Set genes pool      ║");
        System.out.println("\t╠═════════════════════════╣");
        System.out.println("\t║ (t) Set target string   ║");
        System.out.println("\t╠═════════════════════════╣");
        System.out.println("\t║ (p) Set population      ║");
        System.out.println("\t╠═════════════════════════╣");
        System.out.println("\t║ (g) Set max generations ║");
        System.out.println("\t╠═════════════════════════╣");
        System.out.println("\t║ (c) Set cross factor    ║");
        System.out.println("\t╠═════════════════════════╣");
        System.out.println("\t║ (m) Set mutate factor   ║");
        System.out.println("\t╚═════════════════════════╝");
        System.out.println("\t║ (r) Randomize           ║");
        System.out.println("\t╚═════════════════════════╝");
        System.out.println("\tCHOSEN SETTINGS:");
        System.out.println("\tGene pool:\t\t" + GeneticAlgorithm.GENES);
        System.out.println("\tTarget:\t\t\t" + target);
        System.out.println("\tPopulation:\t\t" + population);
        System.out.println("\tMax generations:\t" + maxGenerationsCount);
        System.out.println("\tCross factor:\t\t" + crossFactor);
        System.out.println("\tMutate factor:\t\t" + mutateFactor);
        System.out.println("\n\tChoose option: ");
    }

    private void option(String o) {
        switch (o) {
            case "s":
                GeneticAlgorithm algorithm = new GeneticAlgorithm(
                        target,
                        population,
                        maxGenerationsCount,
                        crossFactor,
                        mutateFactor
                );
                algorithm.start();
                waitForEnter();
                break;
            case "e":
                System.out.print("\n\tEnter new genes pool: ");
                GeneticAlgorithm.GENES = scanner.nextLine();
                while (GeneticAlgorithm.GENES.length() < 2) {
                    System.out.println("\n\tGenes pool has to be greater than 1!");
                    System.out.print("\n\tEnter new genes pool: ");
                    GeneticAlgorithm.GENES = scanner.nextLine();
                }
                break;
            case "t":
                System.out.print("\n\tEnter new target string: ");
                target = scanner.nextLine();
                break;
            case "p":
                System.out.print("\n\tEnter new population size: ");
                population = scanner.nextInt();
                while (population < 1) {
                    System.out.println("\n\tPopulation size has to be greater than 0!");
                    System.out.print("\n\tEnter new population size: ");
                    population = scanner.nextInt();
                }
                break;
            case "g":
                System.out.print("\n\tEnter new maximum generations count (-1 for infinite generations): ");
                maxGenerationsCount = scanner.nextInt();
                while (maxGenerationsCount < -1 || maxGenerationsCount == 0) {
                    System.out.println("\n\tMaximum generations count should be grater than 0 or -1 for infinite generations!");
                    System.out.print("\n\tEnter new maximum generations count: ");
                    maxGenerationsCount = scanner.nextInt();
                }
                break;
            case "c":
                System.out.print("\n\tEnter new cross factor: ");
                crossFactor = scanner.nextDouble();
                while (crossFactor <= 0 || crossFactor > 1) {
                    System.out.println("\n\tCross factor has to be from (0, 1> range!");
                    System.out.print("\n\tEnter new cross factor: ");
                    crossFactor = scanner.nextDouble();
                }
                break;
            case "m":
                System.out.print("\n\tEnter new mutate factor: ");
                mutateFactor = scanner.nextDouble();
                while (mutateFactor <= 0 || mutateFactor > 1) {
                    System.out.println("\n\tMutate factor has to be from (0, 1> range!");
                    System.out.print("\n\tEnter new mutate factor: ");
                    mutateFactor = scanner.nextDouble();
                }
                break;
            case "r":
                target = randomizeTarget();
                population = GeneticAlgorithm.GENERATOR.nextInt(998) + 2;
                maxGenerationsCount = GeneticAlgorithm.GENERATOR.nextInt(10000);
                if (maxGenerationsCount == 0) {
                    maxGenerationsCount = -1;
                }
                do {
                    crossFactor = GeneticAlgorithm.GENERATOR.nextDouble();
                } while (crossFactor == 0);
                do {
                    mutateFactor = GeneticAlgorithm.GENERATOR.nextDouble();
                } while (crossFactor == 0);
                break;
            default:
                break;
        }
    }

    private String randomizeTarget() {
        int length = GeneticAlgorithm.GENERATOR.nextInt(28) + 2;
        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            randomString.append(
                    GeneticAlgorithm.GENES.charAt(
                            GeneticAlgorithm.GENERATOR.nextInt(
                                    GeneticAlgorithm.GENES.length()
                            )
                    )
            );
        }
        return randomString.toString();
    }

    private void cls() {
        for (int i = 0; i < 100; i++)
            System.out.println();
    }

    private void waitForEnter() {
        System.out.println("Press \"Enter\" to continue.");
        scanner.nextLine();
    }

}

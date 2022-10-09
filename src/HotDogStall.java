import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.DoubleStream;

public class HotDogStall {
    private HashMap<Ingredient, Integer> ingredients;
    private double totalSum;
    private double profit;
    private int hotdogsSold;

    public HotDogStall(HashMap<Ingredient, Integer> ingredients, double totalSum) {
        this.ingredients = ingredients;
        this.totalSum = totalSum;
    }

    public HashMap<Ingredient, Integer> getIngredients() {
        return ingredients;
    }

    public void setIngredients(HashMap<Ingredient, Integer> ingredients) {
        this.ingredients = ingredients;
    }

    public double getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(double totalSum) {
        this.totalSum = totalSum;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public int getHotdogsSold() {
        return hotdogsSold;
    }

    public void setHotdogsSold(int hotdogsSold) {
        this.hotdogsSold = hotdogsSold;
    }

    // toString() method
    @Override
    public String toString() {
        return "HotDogStall{" +
                "ingredients present=" + ingredients +
                ", total sum=" + totalSum +
                ", hot-dogs sold=" + hotdogsSold +
                '}';
    }


    // Initial items amount
    public static HashMap<Ingredient, Integer> ingredientsInit() {
        HashMap<Ingredient, Integer> ingredients = new HashMap<>();
        ingredients.put(Ingredient.BUN, 10);
        ingredients.put(Ingredient.SAUSAGE, 10);
        ingredients.put(Ingredient.LETTUCE, 10);
        ingredients.put(Ingredient.PICKLE, 10);
        ingredients.put(Ingredient.ONION, 10);
        ingredients.put(Ingredient.JALAPENO, 10);
        ingredients.put(Ingredient.CHILI, 10);
        ingredients.put(Ingredient.MUSTARD, 10);
        ingredients.put(Ingredient.KETCHUP, 10);
        ingredients.put(Ingredient.MAYONNAISE, 10);
        return ingredients;
    }


    // Checking if we have enough ingredients
    public static boolean ifEnough(HashMap<Ingredient, Integer> ingredients, Ingredient ingredient) {
        int ingredientAmount =
                ingredients.entrySet().stream().
                        filter(x -> x.getKey() == ingredient).
                        findFirst().get().getValue();
        if (ingredientAmount > 0) {
            return true;
        } else {
            System.out.println(ingredient + " wasn't found, sorry.");
            return false;
        }
    }


    // After checking, we gotta delete 1 item from the storage
    public static HashMap<Ingredient, Integer> ingredientDecrement(HashMap<Ingredient, Integer> ingredients, Ingredient ingredient) {
        ingredients.entrySet().forEach(x -> {
            if (x.getKey().equals(ingredient)) {
                x.setValue(x.getValue() - 1);
            }
        });
        return ingredients;
    }


    // Making profit over net cost (ordering 3 or more hot-dogs provides you a discount)
    public void priceWithProfit(ArrayList<Hotdog> hotdogs) {
        if (hotdogs.size() >= 3) {
            this.setTotalSum(this.getTotalSum() + (hotdogs.stream().mapToDouble(Hotdog::hotdogPriceCalculator).sum() * 2));
        } else {
            this.setTotalSum(this.getTotalSum() + (hotdogs.stream().mapToDouble(Hotdog::hotdogPriceCalculator).sum() * 3));
        }
    }

    // Reading info from the file (getting net costs and subtracting them from our total)
    public static double fileReaderMethod(double totalSum) {
        File file = new File(System.getProperty("user.dir") + File.separator + "Hotdogs.txt");
        ArrayList<Hotdog> hotdogs = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            while (fis.available() > 0) {
                Hotdog hotdog = (Hotdog) ois.readObject();
                hotdogs.add(hotdog);
            }
            ois.close();
        } catch (Exception e) {}
        return totalSum - (hotdogs.stream().mapToDouble(Hotdog::hotdogPriceCalculator).sum());
    }


    // Writing info into the file (all the hot-dogs we've sold)
    public static void fileWriterMethod(ArrayList<Hotdog> hotdogs) {
        File file = new File(System.getProperty("user.dir") + File.separator + "Hotdogs.txt");
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file, false));
            for (int i = 0; i < hotdogs.size(); i++) {
                oos.writeObject(hotdogs.get(i));
            }
            oos.close();
        } catch (Exception e) {}
    }


    // Creating basic hot-dog (bun + sausage)
    public static Hotdog basicHotdog(HashMap<Ingredient, Integer> ingredients) {

        // Checking if we have enough components
        if (ifEnough(ingredients, Ingredient.BUN) && ifEnough(ingredients, Ingredient.SAUSAGE)) {
            ingredientDecrement(ingredients, Ingredient.BUN);
            ingredientDecrement(ingredients, Ingredient.SAUSAGE);

            // Adding components into hot-dog
            ArrayList<Ingredient> hotdogIngredients = new ArrayList<>();
            hotdogIngredients.add(Ingredient.BUN);
            hotdogIngredients.add(Ingredient.SAUSAGE);

            // Creating and returning hot-dog
            Hotdog hotdog = new Hotdog(hotdogIngredients);
            double hotdogPrice = hotdog.hotdogPriceCalculator();
            hotdog.setNetPrice(hotdogPrice);
            return hotdog;
        }

        // Or null
        return null;
    }

    // Creating ketchunnaise hot-dog (bun + sausage + ketchup + mayonnaise)
    public static Hotdog ketchunnaiseHotdog(HashMap<Ingredient, Integer> ingredients) {

        // Checking if we have enough components
        if (ifEnough(ingredients, Ingredient.BUN)
                && ifEnough(ingredients, Ingredient.SAUSAGE)
                && ifEnough(ingredients, Ingredient.KETCHUP)
                && ifEnough(ingredients, Ingredient.MAYONNAISE) ) {
            ingredientDecrement(ingredients, Ingredient.BUN);
            ingredientDecrement(ingredients, Ingredient.SAUSAGE);
            ingredientDecrement(ingredients, Ingredient.KETCHUP);
            ingredientDecrement(ingredients, Ingredient.MAYONNAISE);

            // Adding components into hot-dog
            ArrayList<Ingredient> hotdogIngredients = new ArrayList<>();
            hotdogIngredients.add(Ingredient.BUN);
            hotdogIngredients.add(Ingredient.SAUSAGE);
            hotdogIngredients.add(Ingredient.KETCHUP);
            hotdogIngredients.add(Ingredient.MAYONNAISE);

            // Creating and returning hot-dog
            Hotdog hotdog = new Hotdog(hotdogIngredients);
            double hotdogPrice = hotdog.hotdogPriceCalculator();
            hotdog.setNetPrice(hotdogPrice);
            return hotdog;
        }

        // Or null
        return null;
    }

    // Creating vegetable hot-dog (bun + sausage + lettuce + pickle + onion)
    public static Hotdog vegetableHotdog(HashMap<Ingredient, Integer> ingredients) {

        // Checking if we have enough components
        if (ifEnough(ingredients, Ingredient.BUN)
                && ifEnough(ingredients, Ingredient.LETTUCE)
                && ifEnough(ingredients, Ingredient.PICKLE)
                && ifEnough(ingredients, Ingredient.ONION) ) {
            ingredientDecrement(ingredients, Ingredient.BUN);
            ingredientDecrement(ingredients, Ingredient.LETTUCE);
            ingredientDecrement(ingredients, Ingredient.PICKLE);
            ingredientDecrement(ingredients, Ingredient.ONION);

            // Adding components into hot-dog
            ArrayList<Ingredient> hotdogIngredients = new ArrayList<>();
            hotdogIngredients.add(Ingredient.BUN);
            hotdogIngredients.add(Ingredient.LETTUCE);
            hotdogIngredients.add(Ingredient.PICKLE);
            hotdogIngredients.add(Ingredient.ONION);

            // Creating and returning hot-dog
            Hotdog hotdog = new Hotdog(hotdogIngredients);
            double hotdogPrice = hotdog.hotdogPriceCalculator();
            hotdog.setNetPrice(hotdogPrice);
            return hotdog;
        }

        // Or null
        return null;
    }

    // Selling method (amount of hot-dogs, extras needed, pricing)
    public ArrayList<Hotdog> hotdogSelling(HashMap<Ingredient, Integer> ingredients) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // Creating hot-dog list for the customer
        System.out.println("How many hot-dogs do you want to order?");
        ArrayList<Hotdog> hotdogs = new ArrayList<>();

        try {
            int amount = Integer.parseInt(reader.readLine());
            for (int i = 0; i < amount; i++) {
                System.out.println("Hot-dog #" + (i + 1));
                System.out.println("""
                        What hot-dog recipe do you prefer?
                        1. Classic hot-dog.
                        2. Ketchunnaise hot-dog.
                        3. Vegan hot-dog.
                        4. My own.""");

                Hotdog hotdog = new Hotdog();

                int hotdogType = Integer.parseInt(reader.readLine());

                switch (hotdogType) { // Hot-dog types
                    case 1 -> hotdog = basicHotdog(ingredients);
                    case 2 -> hotdog = ketchunnaiseHotdog(ingredients);
                    case 3 -> hotdog = vegetableHotdog(ingredients);
                    case 4 -> hotdog = hotdogWithExtras(ingredients, hotdog);
                }

                if (hotdog != null) {
                    System.out.println("Do you want any extra ingredients or toppings?\n1. Yes.\n2. No.");
                    int extras = Integer.parseInt(reader.readLine());
                    if (extras == 1) {
                        hotdog = hotdogWithExtras(ingredients, hotdog);
                        System.out.println(hotdog);
                        hotdogs.add(hotdog);
                    } else {
                        System.out.println(hotdog);
                        hotdogs.add(hotdog);
                    }
                    this.setHotdogsSold(this.getHotdogsSold() + 1);
                    fileWriterMethod(hotdogs);
                } else {
                    System.out.println("We have a problems with your hot-dog!");
                }
            }

            fileWriterMethod(hotdogs);

        } catch (IOException exception) {
            System.out.println("Incorrect input!");
        }

        return hotdogs;
    }


    // Extra adding method
    public static Hotdog hotdogWithExtras(HashMap<Ingredient, Integer> ingredients, Hotdog hotdog) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // Menu bar
        System.out.println("""
                What ingredient do you want to add?
                1. Bun.
                2. Sausage.
                3. Lettuce.
                4. Pickle.
                5. Onion.
                6. Jalapeno.
                7. Chili.
                8. Mustard.
                9. Ketchup.
                10. Mayonnaise.
                0. Exit.""");

        // Catching exception
        try {
            int userChoice = Integer.parseInt(reader.readLine());
            switch (userChoice) {
                case 0 :
                    System.out.println("Exiting...");
                    break;
                /* For each case checking if we have enough ingredients.
                 * If yes, adding them to hot-dog and deleting from our storage. */
                case 1 :
                    if (ifEnough(ingredients, Ingredient.BUN)) {
                        ingredientDecrement(ingredients, Ingredient.BUN);
                        hotdog.getRecipe().add(Ingredient.BUN);
                    }
                    break;
                case 2 :
                    if (ifEnough(ingredients, Ingredient.SAUSAGE)) {
                        ingredientDecrement(ingredients, Ingredient.SAUSAGE);
                        hotdog.getRecipe().add(Ingredient.SAUSAGE);
                    }
                    break;
                case 3 :
                    if (ifEnough(ingredients, Ingredient.LETTUCE)) {
                        ingredientDecrement(ingredients, Ingredient.LETTUCE);
                        hotdog.getRecipe().add(Ingredient.LETTUCE);
                    }
                    break;
                case 4 :
                    if (ifEnough(ingredients, Ingredient.PICKLE)) {
                        ingredientDecrement(ingredients, Ingredient.PICKLE);
                        hotdog.getRecipe().add(Ingredient.PICKLE);
                    }
                    break;
                case 5 :
                    if (ifEnough(ingredients, Ingredient.ONION)) {
                        ingredientDecrement(ingredients, Ingredient.ONION);
                        hotdog.getRecipe().add(Ingredient.ONION);
                    }
                    break;
                case 6 :
                    if (ifEnough(ingredients, Ingredient.JALAPENO)) {
                        ingredientDecrement(ingredients, Ingredient.JALAPENO);
                        hotdog.getRecipe().add(Ingredient.JALAPENO);
                    }
                    break;
                case 7 :
                    if (ifEnough(ingredients, Ingredient.CHILI)) {
                        ingredientDecrement(ingredients, Ingredient.CHILI);
                        hotdog.getRecipe().add(Ingredient.CHILI);
                    }
                    break;
                case 8 :
                    if (ifEnough(ingredients, Ingredient.MUSTARD)) {
                        ingredientDecrement(ingredients, Ingredient.MUSTARD);
                        hotdog.getRecipe().add(Ingredient.MUSTARD);
                    }
                    break;
                case 9 :
                    if (ifEnough(ingredients, Ingredient.KETCHUP)) {
                        ingredientDecrement(ingredients, Ingredient.KETCHUP);
                        hotdog.getRecipe().add(Ingredient.KETCHUP);
                    }
                    break;
                case 10 :
                    if (ifEnough(ingredients, Ingredient.MAYONNAISE)) {
                        ingredientDecrement(ingredients, Ingredient.MAYONNAISE);
                        hotdog.getRecipe().add(Ingredient.MAYONNAISE);
                    }
                    break;
            }

            System.out.println("Anything else?\n1. Yes.\n2. No.");
            int anythingElse = Integer.parseInt(reader.readLine());
            if (anythingElse == 1) {
                hotdogWithExtras(ingredients, hotdog);
            }

        } catch (IOException exception) {
            System.out.println("Incorrect input!");
        }

        double hotdogPrice = hotdog.hotdogPriceCalculator();
        hotdog.setNetPrice(hotdogPrice);
        return hotdog;
    }


    // Menu method
    public static void menu(HotDogStall hotDogStall) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("""
                What do you want to do?
                1. Order a hot-dog.
                2. Get stats.
                3. Exit.""");

        try {
            int userChoice = Integer.parseInt(reader.readLine());
            switch (userChoice) {
                case 1 :
                    hotDogStall.priceWithProfit(hotDogStall.hotdogSelling(hotDogStall.getIngredients())); // Selling method inside set profit method
                    menu(hotDogStall); // Menu calling
                    break;
                case 2 :
                    System.out.println(hotDogStall.toString()); // Shows stall's stats
                    System.out.println("Total sum: " + hotDogStall.getTotalSum()); // Shows stall's total sum
                    System.out.println("Total profit (for current session only): " + fileReaderMethod(hotDogStall.getTotalSum())); // Shows profit for current session
                    menu(hotDogStall); // Menu calling
                    break;
                case 3 :
                    System.out.println("Exiting...");
                    break; // Exit
            }
        } catch (Exception exception) {
            System.out.println("Incorrect input!");
        }
    }
}
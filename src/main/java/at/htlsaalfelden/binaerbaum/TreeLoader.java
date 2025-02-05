package at.htlsaalfelden.binaerbaum;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;

public class TreeLoader {
    public static BinaryTree load(File path) throws FileNotFoundException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        BinaryTree tree = gson.fromJson(new FileReader(path), BinaryTree.class);
        tree.afterInit();
        return tree;
    }

    public static void save(File path, BinaryTree tree) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        FileWriter writer = new FileWriter(path);
        gson.toJson(tree, writer);
        writer.close();
    }
}

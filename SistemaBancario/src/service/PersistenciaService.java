package service;

import com.google.gson.*;
import model.*;
import java.io.*;

public class PersistenciaService {
    private static final String ARQUIVO = "contas.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void salvar(java.util.List<Conta> contas) {
        try (FileWriter writer = new FileWriter(ARQUIVO)) {
            JsonArray jsonArray = new JsonArray();
            for (Conta conta : contas) {
                JsonObject obj = gson.toJsonTree(conta).getAsJsonObject();
                obj.addProperty("tipoConta", conta instanceof ContaPoupanca ? "POUPANCA" : "CORRENTE");
                jsonArray.add(obj);
            }
            gson.toJson(jsonArray, writer);
        } catch (IOException e) {
            System.err.println("Erro ao salvar: " + e.getMessage());
        }
    }

    public static void carregar(BancoService banco) {
        File file = new File(ARQUIVO);
        if (!file.exists()) return;

        try (FileReader reader = new FileReader(ARQUIVO)) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            if (jsonElement.isJsonNull()) return;
            
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (JsonElement element : jsonArray) {
                JsonObject obj = element.getAsJsonObject();
                String tipo = obj.get("tipoConta").getAsString();
                
                Conta conta;
                if ("POUPANCA".equals(tipo)) {
                    conta = gson.fromJson(obj, ContaPoupanca.class);
                } else {
                    conta = gson.fromJson(obj, ContaCorrente.class);
                }
                banco.adicionarConta(conta);
            }
        } catch (Exception e) {
            System.err.println("Erro ao carregar: " + e.getMessage());
        }
    }
}
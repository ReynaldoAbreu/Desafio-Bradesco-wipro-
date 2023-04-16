package com.reynaldoabreu.addressapi.model.repository;

import java.util.HashMap;
import java.util.Map;

public class Region {
    private Map<String, String[]> regions;

    public Region() {
        regions = new HashMap<>();
        regions.put("sudeste", new String[]{"SP", "RJ", "MG", "ES"});
        regions.put("norte", new String[]{"AM", "PA", "AC", "RO", "AP", "TO"});
        regions.put("sul", new String[]{"PR", "SC", "RS"});
        regions.put("centroOeste", new String[]{"GO", "MT", "MS", "DF"});
        regions.put("nordeste", new String[]{"BA", "MA", "PE", "CE", "PB", "RN", "AL"});
    }

    public String getRegionByUF(String uf) {
        for (Map.Entry<String, String[]> entry : regions.entrySet()) {
            String regionName = entry.getKey();
            String[] ufList = entry.getValue();
            for (String ufInList : ufList) {
                if (uf.equals(ufInList)) {
                    return regionName;
                }
            }
        }
        return "Região não encontrada";
    }

}
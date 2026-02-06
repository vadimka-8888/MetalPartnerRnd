package ru.metal_partner_rnd.ecommerce_core.utils;

import java.util.List;

public interface AuxiliaryFilesIntroducer {

    public List<String> getFilesToInsert(String base);

    public List<String> getJs(Integer type, String base);
}
package jzs.test.base.util;

import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

public class TypeConvertService {
    
    private static class ConvertServiceHolder {
        private static final ConversionService service = new DefaultConversionService();    
    }
    
    private TypeConvertService() {
        
    }
    
    public static final ConversionService getInstance() {
        return ConvertServiceHolder.service;
    }
    
}

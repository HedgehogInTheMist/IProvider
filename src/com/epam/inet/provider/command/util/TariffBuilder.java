package com.epam.inet.provider.command.util;

import com.epam.inet.provider.entity.TariffType;
import com.epam.inet.provider.entity.Tariff;
import com.epam.inet.provider.command.exception.BuildException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Build a tariff object from request
 */
public class TariffBuilder extends EntityBuilder<Tariff> {

    private static final Logger LOGGER = LogManager.getLogger(TariffBuilder.class);

    public static final String TARIFFNAME = "tariffname";
    public static final String PRICE = "price";
    public static final String DETAILS = "details";
    public static final String REGULAR_DISCOUNT = "regular_discount";
//    public static final String

    @Override
    public Tariff build(Map<String, String[]> map) throws BuildException {
        Tariff tariff = new Tariff();
        build(map, tariff);

        return tariff;
    }

    /**
     * Fill entity
     * @param map
     * @param tariff
     * @throws BuildException
     */
    public void build(Map<String, String[]> map, Tariff tariff) throws BuildException{
        boolean tariffname = !buildTariffname(map.get(TARIFFNAME), tariff);
        boolean price = !buildPrice(map.get(PRICE), tariff);
        boolean details = !buildDetails(map.get(DETAILS), tariff);
        boolean discount = !buildDiscount(map.get(REGULAR_DISCOUNT), tariff);
//        boolean hot = !buildHot(map.get("hot"), tariff);
        boolean type = !buildType(map.get("type"), tariff);

/*
        boolean tariffname = !buildTariffname(map.get("serviceName"), tariff);
        boolean price = !buildPrice(map.get("price"), tariff);
        boolean details = !buildDetails(map.get("description"), tariff);
        boolean discount = !buildDiscount(map.get("discount"), tariff);
        boolean hot = !buildHot(map.get("hot"), tariff);
        boolean type = !buildType(map.get("type"), tariff);
*/

        /*
        LOGGER.debug(tariffname);
        LOGGER.debug(price);
        LOGGER.debug(details);
        LOGGER.debug(discount);
        LOGGER.debug(hot);
        LOGGER.debug(type);
        */

        if (tariffname || details || price || discount || type){
            throw new BuildException();
        }

    }

    /**
     * build tariff Tariffname
     * @param args parameter values
     * @param tariff target tariff
     */
    public boolean buildTariffname(String[] args, Tariff tariff) {
        if (args != null && args.length > 0){
            String tariffname = args[0];
            if (tariffname.length() > 0){
                try {
                    tariff.setTariffname(new String(args[0].getBytes("ISO-8859-1"),"UTF-8"));

                } catch (UnsupportedEncodingException e) {
                    LOGGER.error(e);
                }
                return true;
            }

        }
        return false;
    }


    /**
     * build tariff Price
     * @param args parameter values
     * @param tariff target tariff
     */
    public boolean buildPrice(String[] args, Tariff tariff) {
        if (args != null && args.length > 0 && args[0].length() > 0){
            try{
                int price = Integer.parseInt(args[0]);
                if (price > 0) {
                    tariff.setPrice(price);
                    return true;
                } else{
                    return false;
                }
            } catch (NumberFormatException e){
                return false;
            }
        }
        return false;
    }

    /**
     * build tariff Discount
     * @param args parameter values
     * @param tariff target tariff
     */
    public boolean buildDiscount(String[] args, Tariff tariff) {
        if (args != null && args.length > 0 && args[0].length() > 0){
            try{
                int discount = Integer.parseInt(args[0]);
                if (discount >= 0 && discount < 100) {
                    tariff.setRegularDiscount(discount);
                    return true;
                } else{
                    return false;
                }
            } catch (NumberFormatException e){
                return false;
            }
        }

        return false;
    }

    /**
     * build tariff hot
     * @param args parameter values
     * @param tariff target tariff
     */
    public boolean buildHot(String[] args, Tariff tariff) {
        if (args != null && args.length > 0 && args[0].length() > 0){
            tariff.setHot(true);

        } else{
            tariff.setHot(false);
        }
        return true;
    }

    /**
     * build tariff Details
     * @param args parameter values
     * @param tariff target tariff
     */
    public boolean buildDetails(String[] args, Tariff tariff) {
        if (args != null && args.length > 0){
            String details = args[0];
            if (details.length() > 0){

                try {
                    tariff.setDetails(new String(args[0].getBytes("ISO-8859-1"),"UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    LOGGER.error(e);
                }

                return true;
            }

        }
        return false;
    }

    /**
     * build tariff Type
     * @param args parameter values
     * @param tariff target tariff
     */
    public boolean buildType(String[] args, Tariff tariff) {
        if (args != null && args.length > 0 && args[0].length() > 0){
            try{
                int typeID = Integer.parseInt(args[0]);
                TariffType type = TariffType.findById(typeID);
                if (type != null){
                    tariff.setType(type);
                    return true;
                } else{
                    return false;
                }
            } catch (NumberFormatException e){
                return false;
            }
        }

        return false;
    }
}

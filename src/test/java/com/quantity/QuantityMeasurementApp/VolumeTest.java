package com.quantity.QuantityMeasurementApp;

import com.quantity.QuantityMeasurementApp.enums.VolumeUnit;
import com.quantity.QuantityMeasurementApp.model.Quantity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VolumeTest {

    @Test
    void oneLitreEqualsThousandMilliLitre() {

        Quantity litre = new Quantity(1, VolumeUnit.LITRE);
        Quantity ml = new Quantity(1000, VolumeUnit.MILLILITRE);

        Assertions.assertEquals(litre, ml);
    }

    @Test
    void oneGallonEquals3Point78Litre() {

        Quantity gallon = new Quantity(1, VolumeUnit.GALLON);
        Quantity litre = new Quantity(3.78, VolumeUnit.LITRE);

        Assertions.assertEquals(gallon, litre);
    }

    @Test
    void convertLitreToMilliLitre() {

        Quantity litre = new Quantity(1, VolumeUnit.LITRE);
        Quantity result = litre.convertTo(VolumeUnit.MILLILITRE);

        Assertions.assertEquals(new Quantity(1000, VolumeUnit.MILLILITRE), result);
    }

    @Test
    void additionLitrePlusMilliLitre() {

        Quantity litre = new Quantity(1, VolumeUnit.LITRE);
        Quantity ml = new Quantity(500, VolumeUnit.MILLILITRE);

        Quantity result = litre.add(ml);

        Assertions.assertEquals(new Quantity(1.5, VolumeUnit.LITRE), result);
    }

    @Test
    void additionGallonPlusLitre() {

        Quantity gallon = new Quantity(1, VolumeUnit.GALLON);
        Quantity litre = new Quantity(3.78, VolumeUnit.LITRE);

        Quantity result = gallon.add(litre);

        Assertions.assertEquals(new Quantity(2, VolumeUnit.GALLON), result);
    }

}
package fr.timeto.astrauworld.launcher.customelements;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 *  The HSLColor class provides methods to manipulate HSL (Hue, Saturation
 *  Luminance) values to create a corresponding Color object using the RGB
 *  ColorSpace.
 *
 *  The HUE is the color, the Saturation is the purity of the color (with
 *  respect to grey) and Luminance is the brightness of the color (with respect
 *  to black and white)
 *
 *  The Hue is specified as an angel between 0 - 360 degrees where red is 0,
 *  green is 120 and blue is 240. In between you have the colors of the rainbow.
 *  Saturation is specified as a percentage between 0 - 100 where 100 is fully
 *  saturated and 0 approaches gray. Luminance is specified as a percentage
 *  between 0 - 100 where 0 is black and 100 is white.
 *
 *  In particular the HSL color space makes it easier change the Tone or Shade
 *  of a color by adjusting the luminance value.
 */
public class HSLColor
{
    private Color rgb;
    private float[] hsl;
    private float alpha;

    /**
     *  Create a HSLColor object using an RGB Color object.
     *
     *  @param rgb the RGB Color object
     */
    public HSLColor(Color rgb)
    {
        this.rgb = rgb;
        hsl = fromRGB( rgb );
        alpha = rgb.getAlpha() / 255.0f;
    }

    /**
     *  Create a HSLColor object using individual HSL values and a default
     * alpha value of 1.0.
     *
     *  @param h is the Hue value in degrees between 0 - 360
     *  @param s is the Saturation percentage between 0 - 100
     *  @param l is the Lumanance percentage between 0 - 100
     */
    public HSLColor(float h, float s, float l)
    {
        this(h, s, l, 1.0f);
    }

    /**
     *  Create a HSLColor object using individual HSL values.
     *
     *  @param h     the Hue value in degrees between 0 - 360
     *  @param s     the Saturation percentage between 0 - 100
     *  @param l     the Lumanance percentage between 0 - 100
     *  @param alpha the alpha value between 0 - 1
     */
    public HSLColor(float h, float s, float l, float alpha)
    {
        hsl = new float[] {h, s, l};
        this.alpha = alpha;
        rgb = toRGB(hsl, alpha);
    }

    /**
     *  Create a HSLColor object using an an array containing the
     *  individual HSL values and with a default alpha value of 1.
     *
     *  @param hsl  array containing HSL values
     */
    public HSLColor(float[] hsl)
    {
        this(hsl, 1.0f);
    }

    /**
     *  Create a HSLColor object using an an array containing the
     *  individual HSL values.
     *
     *  @param hsl  array containing HSL values
     *  @param alpha the alpha value between 0 - 1
     */
    public HSLColor(float[] hsl, float alpha)
    {
        this.hsl = hsl;
        this.alpha = alpha;
        rgb = toRGB(hsl, alpha);
    }

    /**
     *  Create a RGB Color object based on this HSLColor with a different
     *  Hue value. The degrees specified is an absolute value.
     *
     *  @param degrees - the Hue value between 0 - 360
     *  @return the RGB Color object
     */
    public Color adjustHue(float degrees)
    {
        return toRGB(degrees, hsl[1], hsl[2], alpha);
    }

    /**
     *  Create a RGB Color object based on this HSLColor with a different
     *  Luminance value. The percent specified is an absolute value.
     *
     *  @param percent - the Luminance value between 0 - 100
     *  @return the RGB Color object
     */
    public Color adjustLuminance(float percent)
    {
        return toRGB(hsl[0], hsl[1], percent, alpha);
    }

    /**
     *  Create a RGB Color object based on this HSLColor with a different
     *  Saturation value. The percent specified is an absolute value.
     *
     *  @param percent - the Saturation value between 0 - 100
     *  @return the RGB Color object
     */
    public Color adjustSaturation(float percent)
    {
        return toRGB(hsl[0], percent, hsl[2], alpha);
    }

    /**
     *  Create a RGB Color object based on this HSLColor with a different
     *  Shade. Changing the shade will return a darker color. The percent
     *  specified is a relative value.
     *
     *  @param percent - the value between 0 - 100
     *  @return the RGB Color object
     */
    public Color adjustShade(float percent)
    {
        float multiplier = (100.0f - percent) / 100.0f;
        float l = Math.max(0.0f, hsl[2] * multiplier);

        return toRGB(hsl[0], hsl[1], l, alpha);
    }

    /**
     *  Create a RGB Color object based on this HSLColor with a different
     *  Tone. Changing the tone will return a lighter color. The percent
     *  specified is a relative value.
     *
     *  @param percent - the value between 0 - 100
     *  @return the RGB Color object
     */
    public Color adjustTone(float percent)
    {
        float multiplier = (100.0f + percent) / 100.0f;
        float l = Math.min(100.0f, hsl[2] * multiplier);

        return toRGB(hsl[0], hsl[1], l, alpha);
    }

    /**
     *  Get the Alpha value.
     *
     *  @return the Alpha value.
     */
    public float getAlpha()
    {
        return alpha;
    }

    /**
     *  Create a RGB Color object that is the complementary color of this
     *  HSLColor. This is a convenience method. The complementary color is
     *  determined by adding 180 degrees to the Hue value.
     *  @return the RGB Color object
     */
    public Color getComplementary()
    {
        float hue = (hsl[0] + 180.0f) % 360.0f;
        return toRGB(hue, hsl[1], hsl[2]);
    }

    /**
     *  Get the Hue value.
     *
     *  @return the Hue value.
     */
    public float getHue()
    {
        return hsl[0];
    }

    /**
     *  Get the HSL values.
     *
     *  @return the HSL values.
     */
    public float[] getHSL()
    {
        return hsl;
    }

    /**
     *  Get the Luminance value.
     *
     *  @return the Luminance value.
     */
    public float getLuminance()
    {
        return hsl[2];
    }

    /**
     *  Get the RGB Color object represented by this HDLColor.
     *
     *  @return the RGB Color object.
     */
    public Color getRGB()
    {
        return rgb;
    }

    /**
     *  Get the Saturation value.
     *
     *  @return the Saturation value.
     */
    public float getSaturation()
    {
        return hsl[1];
    }

    public String toString()
    {
        String toString =
                "HSLColor[h=" + hsl[0] +
                        ",s=" + hsl[1] +
                        ",l=" + hsl[2] +
                        ",alpha=" + alpha + "]";

        return toString;
    }

    /**
     *  Convert a RGB Color to it corresponding HSL values.
     *
     *  @return an array containing the 3 HSL values.
     */
    public static float[] fromRGB(Color color)
    {
        //  Get RGB values in the range 0 - 1

        float[] rgb = color.getRGBColorComponents( null );
        float r = rgb[0];
        float g = rgb[1];
        float b = rgb[2];

        //	Minimum and Maximum RGB values are used in the HSL calculations

        float min = Math.min(r, Math.min(g, b));
        float max = Math.max(r, Math.max(g, b));

        //  Calculate the Hue

        float h = 0;

        if (max == min)
            h = 0;
        else if (max == r)
            h = ((60 * (g - b) / (max - min)) + 360) % 360;
        else if (max == g)
            h = (60 * (b - r) / (max - min)) + 120;
        else if (max == b)
            h = (60 * (r - g) / (max - min)) + 240;

        //  Calculate the Luminance

        float l = (max + min) / 2;

        //  Calculate the Saturation

        float s = 0;

        if (max == min)
            s = 0;
        else if (l <= .5f)
            s = (max - min) / (max + min);
        else
            s = (max - min) / (2 - max - min);

        return new float[] {h, s * 100, l * 100};
    }

    /**
     *  Convert HSL values to a RGB Color with a default alpha value of 1.
     *  H (Hue) is specified as degrees in the range 0 - 360.
     *  S (Saturation) is specified as a percentage in the range 1 - 100.
     *  L (Lumanance) is specified as a percentage in the range 1 - 100.
     *
     *  @param hsl an array containing the 3 HSL values
     *
     *  @returns the RGB Color object
     */
    public static Color toRGB(float[] hsl)
    {
        return toRGB(hsl, 1.0f);
    }

    /**
     *  Convert HSL values to a RGB Color.
     *  H (Hue) is specified as degrees in the range 0 - 360.
     *  S (Saturation) is specified as a percentage in the range 1 - 100.
     *  L (Lumanance) is specified as a percentage in the range 1 - 100.
     *
     *  @param hsl    an array containing the 3 HSL values
     *  @param alpha  the alpha value between 0 - 1
     *
     *  @returns the RGB Color object
     */
    public static Color toRGB(float[] hsl, float alpha)
    {
        return toRGB(hsl[0], hsl[1], hsl[2], alpha);
    }

    /**
     *  Convert HSL values to a RGB Color with a default alpha value of 1.
     *
     *  @param h Hue is specified as degrees in the range 0 - 360.
     *  @param s Saturation is specified as a percentage in the range 1 - 100.
     *  @param l Lumanance is specified as a percentage in the range 1 - 100.
     *
     *  @returns the RGB Color object
     */
    public static Color toRGB(float h, float s, float l)
    {
        return toRGB(h, s, l, 1.0f);
    }

    /**
     *  Convert HSL values to a RGB Color.
     *
     *  @param h Hue is specified as degrees in the range 0 - 360.
     *  @param s Saturation is specified as a percentage in the range 1 - 100.
     *  @param l Lumanance is specified as a percentage in the range 1 - 100.
     *  @param alpha  the alpha value between 0 - 1
     *
     *  @returns the RGB Color object
     */
    public static Color toRGB(float h, float s, float l, float alpha)
    {
        if (s <0.0f || s > 100.0f)
        {
            String message = "Color parameter outside of expected range - Saturation";
            throw new IllegalArgumentException( message );
        }

        if (l <0.0f || l > 100.0f)
        {
            String message = "Color parameter outside of expected range - Luminance";
            throw new IllegalArgumentException( message );
        }

        if (alpha <0.0f || alpha > 1.0f)
        {
            String message = "Color parameter outside of expected range - Alpha";
            throw new IllegalArgumentException( message );
        }

        //  Formula needs all values between 0 - 1.

        h = h % 360.0f;
        h /= 360f;
        s /= 100f;
        l /= 100f;

        float q = 0;

        if (l < 0.5)
            q = l * (1 + s);
        else
            q = (l + s) - (s * l);

        float p = 2 * l - q;

        float r = Math.max(0, HueToRGB(p, q, h + (1.0f / 3.0f)));
        float g = Math.max(0, HueToRGB(p, q, h));
        float b = Math.max(0, HueToRGB(p, q, h - (1.0f / 3.0f)));

        r = Math.min(r, 1.0f);
        g = Math.min(g, 1.0f);
        b = Math.min(b, 1.0f);

        return new Color(r, g, b, alpha);
    }

    private static float HueToRGB(float p, float q, float h)
    {
        if (h < 0) h += 1;

        if (h > 1 ) h -= 1;

        if (6 * h < 1)
        {
            return p + ((q - p) * 6 * h);
        }

        if (2 * h < 1 )
        {
            return  q;
        }

        if (3 * h < 2)
        {
            return p + ( (q - p) * 6 * ((2.0f / 3.0f) - h) );
        }

        return p;
    }


    public static Color getComplementaryColor(Color colorToInvert) {
        float[] hsb = new float[3];
        Color.RGBtoHSB(colorToInvert.getRed(), colorToInvert.getGreen(),
                colorToInvert.getBlue(), hsb);
        hsb[0] = (hsb[0] + 0.180f) % 360;
        return new Color(Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
    }

    public static Color getContrastVersionForColor(Color color, boolean onGreyscale) {
        HSLColor hsl = new HSLColor(color);

        float l;
        if (hsl.getLuminance() < 50) {
            if (hsl.getLuminance() < 20)
                l = 90;
            else
                l = 70;
        } else {
            if (hsl.getLuminance() < 90)
                l= 20;
            else
                l = 40;
        }

        float s;
        if (onGreyscale) {
            s = 0;
        } else {
            s = hsl.getSaturation() * 20;
        }
        return new HSLColor(hsl.getHue(), s, l).getRGB();
    }

    public static Color getDominantColor(BufferedImage image) {
        int height = image.getHeight();
        int width = image.getWidth();

        Map m = new HashMap();
        for(int i=0; i < width ; i++)
        {
            for(int j=0; j < height ; j++)
            {
                int rgb = image.getRGB(i, j);
                int[] rgbArr = getRGBArr(rgb);
                // Filter out grays....
                if (!isGray(rgbArr)) {
                    Integer counter = (Integer) m.get(rgb);
                    if (counter == null)
                        counter = 0;
                    counter++;
                    m.put(rgb, counter);
                }
            }
        }
        return getMostCommonColour(m);
    }

    public static Color getMostCommonColour(Map map) {
        LinkedList list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o1)).getValue())
                        .compareTo(((Map.Entry) (o2)).getValue());
            }
        });
        Map.Entry me = (Map.Entry )list.get(list.size()-1);
        int[] rgb= getRGBArr((Integer)me.getKey());
        return new Color(rgb[0], rgb[1], rgb[2]);
    }

    public static int[] getRGBArr(int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        return new int[]{red,green,blue};

    }
    public static boolean isGray(int[] rgbArr) {
        int rgDiff = rgbArr[0] - rgbArr[1];
        int rbDiff = rgbArr[0] - rgbArr[2];
        // Filter out black, white and grays...... (tolerance within 10 pixels)
        int tolerance = 10;
        if (rgDiff > tolerance || rgDiff < -tolerance)
            return rbDiff <= tolerance && rbDiff >= -tolerance;
        return true;
    }

    public static Color getColorDarker(Color color, int dark) {
        int r = color.getRed() - dark;
        int g = color.getGreen() - dark;
        int b = color.getBlue() - dark;

        if (r < 0) {
            r = 0;
        }
        if (g < 0) {
            g = 0;
        }
        if (b < 0) {
            b = 0;
        }

        return new Color(r, g, b, color.getAlpha());
    }

    public static Color getColorLighter(Color color, int light) {
        int r = color.getRed() + light;
        int g = color.getGreen() + light;
        int b = color.getBlue() + light;

        if (r > 255) {
            r = 255;
        }
        if (g > 255) {
            g = 255;
        }
        if (b > 255) {
            b = 255;
        }

        return new Color(r, g, b, color.getAlpha());
    }
}
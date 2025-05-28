package dk.sdu.mmmi.cbse.common.data;

public enum Color {
    RED(255, 0, 0, 1), AZURE(240, 255, 255, 1), GREEN(0, 100, 0, 1),
    VIOLET(238,130,238,1), GOLD(255,215,0, 1), SNOW(255,250,250, 1),
    HONEYDEW(240,255,240,1), BEIGE(245,245,220, 1), MISTYROSE(255,228,225,1);

    final public double r;
    final public double g;
    final public double b;
    final public double a;
    Color(double r, double g, double b, double a){
        this.r = r/255;
        this.g = g/255;
        this.b = b/255;
        this.a = a;
    }
}
import java.util.function.BiFunction;
import java.io.*;

public abstract class Shape implements Serializable{
    protected static String color = "Pink";

    public abstract double calculateArea();
    public abstract double calculatePerimeter();
    public static void displayColor(){
        System.out.println("The color of all shapes is:" + color);
    }
}

//GeneralShape given by a function with 2 variables, and boundary
class GeneralShape extends Shape implements Serializable{
    //private static final long serialVersionUID = 1L;
    //shapeFunction not being serialized
    private transient BiFunction<Double, Double, Boolean> shapeFunction;
    private double xMin, xMax, yMin, yMax;

    public GeneralShape(BiFunction<Double, Double, Boolean> shapeFunction, double xMin, double xMax, double yMin, double yMax){
        this.shapeFunction = shapeFunction;
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
    }

    private Object readResolve() {
        // Reassign shapeFunction manually after deserialization
        this.shapeFunction = (x, y) -> x * x + y * y <= 4;  // Example function, adjust as needed
        return this;
    }

    @Override
    public double calculateArea(){
        double area = 0;
        int numSteps = 1000;//determines accuracy
        //define infinitesimal
        double dx = (xMax - xMin)/numSteps;
        double dy = (yMax - yMin)/numSteps;
        //use calculus for area calculation
        for(double x = xMin; x <= xMax; x += dx){
            for(double y = yMin; y <= yMax; y += dy){
                if(shapeFunction.apply(x,y)){
                    area += dx*dy;
                }
            }
        }
        System.out.println("The calculated area of the shape is:" + area);
        return area;
    }

    @Override
    public double calculatePerimeter(){
        double perimeter = 0;
        int numSteps = 1000;
        //define infinitesimal
        double dx = (xMax - xMin)/numSteps;
        double dy = (yMax - yMin)/numSteps;

        for(double x = xMin; x <= xMax; x += dx){
            for(double y = yMin; y <= yMax; y += dy){
                //determine boundary
                boolean isInside = shapeFunction.apply(x, y);
                boolean neighborXOutside = !shapeFunction.apply(x+dx, y);
                boolean neighborYOutside = !shapeFunction.apply(x, y+dy);

                if (isInside && (neighborXOutside || neighborYOutside)) {
                    //Estimate perimeter based on the grid resolution
                    if (neighborXOutside) perimeter += dx;  //Vertical boundary
                    if (neighborYOutside) perimeter += dy;  //Horizontal boundary
                    //Add diagonal boundary approximation
                    if (neighborXOutside && neighborYOutside) {
                        perimeter += Math.sqrt(dx * dx + dy * dy);
                    }
                }
            }
        }
        System.out.println("The perimeter of the function is:" + perimeter);
        return perimeter;
    }
}

//Triangle
class Triangle extends Shape{
    private static final long serialVersionUID = 1L;
    private double base;
    private double height;
    private double side1, side2, side3;

    public Triangle(double base, double height, double side1, double side2, double side3){
        this.base = base;
        this.height = height;
        this.side1 = side1;
        this.side2 = side2;
        this.side3 = side3;
    }

    @Override
    public double calculateArea(){
        double area = 0.5 * base * height;
        System.out.println("The area of the triangle is:" + area);
        return area;
    }

    @Override
    public double calculatePerimeter(){
        double perimeter = side1 + side2 + side3;
        System.out.println("The perimeter of the triangle is:" + perimeter);
        return perimeter;
    }
}

class Rectangle extends Shape{
    private double length;
    private double width;

    public Rectangle(double length, double width){
        this.length = length;
        this.width = width;
    }

    @Override
    public double calculateArea(){
        double area = length * width;
        System.out.println("The area of the rectangle is:" + area);
        return area;
    }

    @Override
    public double calculatePerimeter(){
        double perimeter = 2 * (length + width);
        System.out.println("The perimeter of the rectangle is:" + perimeter);
        return perimeter;
    }
}

class Circle extends Shape{
    private static final long serialVersionUID = 1L;
    private double radius;

    public Circle(double radius){
        this.radius = radius;
    }

    @Override
    public double calculateArea(){
        double area = Math.PI * radius * radius;
        System.out.println("The area of the circle is:" + area);
        return area;
    }

    @Override
    public double calculatePerimeter(){
        double perimeter = 2 * Math.PI *radius;
        System.out.println("The perimeter of the circle is:" + perimeter);
        return perimeter;
    }
}

class Square extends Rectangle{
    private static final long serialVersionUID = 1L;
    public Square(double side){
        super(side, side);
    }

}

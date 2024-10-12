//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //Example: Circle
        Shape circle = new Circle(2.0);
        circle.calculateArea();
        circle.calculatePerimeter();

        //Example: Triangle
        Shape triangle = new Triangle(3.0, 4.0, 3.0, 4.0, 5.0);
        triangle.calculateArea();
        triangle.calculatePerimeter();

        //Example: Rectangle
        Shape rectangle = new Rectangle(3.0, 4.0);
        rectangle.calculateArea();
        rectangle.calculatePerimeter();

        //Example: Square
        Shape square = new Square(5.0);
        square.calculateArea();
        square.calculatePerimeter();

        //Example: General integrable function shape
        Shape generalShape = new GeneralShape((x,y) -> x*x + y*y <= 4, -1, 1, -2, 2);
        generalShape.calculateArea();
        generalShape.calculatePerimeter();
        Shape.displayColor();
    }
}
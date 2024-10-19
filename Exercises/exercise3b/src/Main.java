import java.io.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //Example: Circle
        Shape circle = new Circle(2.0);
        //Example: Triangle
        Shape triangle = new Triangle(3.0, 4.0, 3.0, 4.0, 5.0);
        //Example: Rectangle
        Shape rectangle = new Rectangle(3.0, 4.0);
        //Example: Square
        Shape square = new Square(5.0);
        //Example: General integrable function shape
        Shape generalShape = new GeneralShape((x,y) -> x*x + y*y <= 4, -1, 1, -2, 2);

        //Serialization
        serializeShape(circle, "circle.ser");
        serializeShape(triangle, "triangle.ser");
        serializeShape(rectangle, "rectangle.ser");
        serializeShape(square, "square.ser");
        serializeShape(generalShape, "generalShape.ser");

        //Deserialization
        Shape deserializedCircle = deserializeShape("circle.ser");
        Shape deserializedTriangle = deserializeShape("triangle.ser");
        Shape deserializedRectangle = deserializeShape("rectangle.ser");
        Shape deserializedSquare = deserializeShape("square.ser");
        Shape deserializedGeneralShape = deserializeShape("generalShape.ser");

        //Test
        deserializedCircle.calculateArea();
        deserializedCircle.calculatePerimeter();

        deserializedTriangle.calculateArea();
        deserializedTriangle.calculatePerimeter();

        deserializedRectangle.calculateArea();
        deserializedRectangle.calculatePerimeter();

        deserializedSquare.calculateArea();
        deserializedSquare.calculatePerimeter();

        deserializedGeneralShape.calculateArea();
        deserializedGeneralShape.calculatePerimeter();

        Shape.displayColor();
    }

    //Serialize a shape
    public static void serializeShape(Shape shape, String filename){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))){
            oos.writeObject(shape);
            System.out.println("Serialized data of " + shape.getClass().getSimpleName() + " is saved in " + filename);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    //Deserialize a shape
    public static Shape deserializeShape(String filename){
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))){
            Shape shape = (Shape) ois.readObject();
            System.out.println("Deserialized data of " + shape.getClass().getSimpleName() + " from " + filename);
            return shape;
        }catch (IOException | ClassNotFoundException e) {
            System.out.println("Shape class not found");
            e.printStackTrace();
            return null;
        }
    }
}
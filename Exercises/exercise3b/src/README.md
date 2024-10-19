This is the description for the exercise 3b.

This exercise is based on exercise 2, and makes the classes in exercise 2 serializable.
However, shapeFunction is a transient and not being serialized, since BiFunction isn't serializable.


The output will be like:

Serialized data of Circle is saved in circle.ser

Serialized data of Triangle is saved in triangle.ser

Serialized data of Rectangle is saved in rectangle.ser

Serialized data of Square is saved in square.ser

Serialized data of GeneralShape is saved in generalShape.ser

Deserialized data of Circle from circle.ser

Deserialized data of Triangle from triangle.ser

Deserialized data of Rectangle from rectangle.ser

Deserialized data of Square from square.ser

Deserialized data of GeneralShape from generalShape.ser

The area of the circle is:12.566370614359172

The perimeter of the circle is:12.566370614359172

The area of the triangle is:6.0

The perimeter of the triangle is:12.0

The area of the rectangle is:12.0

The perimeter of the rectangle is:14.0

The area of the rectangle is:25.0

The perimeter of the rectangle is:20.0

The calculated area of the shape is:7.6524400000533515

The perimeter of the function is:4.561160973029911

The color of all shapes is:Pink
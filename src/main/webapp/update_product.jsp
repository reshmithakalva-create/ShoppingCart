<html>
<body>
    <h2>Update Product</h2>

    <form action="updateProduct" method="post">

        Product ID:
        <input type="number" name="id" required>
        <br><br>

        New Product Name:
        <input type="text" name="name" required>
        <br><br>

        New Price:
        <input type="number" step="0.01" name="price" required>
        <br><br>

        New Quantity:
        <input type="number" name="quantity" required>
        <br><br>

        <input type="submit" value="Update Product">

    </form>
</body>
</html>
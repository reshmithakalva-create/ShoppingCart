<html>
<body>
    <h2>Add New Product</h2>

    <form action="addProduct" method="post">
        Product ID: <input type="number" name="id" required><br><br>

        Product Name: <input type="text" name="name" required><br><br>

        Price: <input type="number" step="0.01" name="price" required><br><br>

        Quantity: <input type="number" name="quantity" required><br><br>

        <input type="submit" value="Add Product">
    </form>
</body>
</html>
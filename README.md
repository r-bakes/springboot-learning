# BookStore
> 💡 BookStore is a simple SpringBoot Web-app learning project built on one day.

## Features

1. Manage the bookstore inventory
   - Add a new book, stock and inventory to the shop
   - Remove a book
   - Update a book 
3. Manage a users cart
   - Add books
   - Remove books
      Checkout

4. Production ready API documentation via [Swagger](https://swagger.io/specification/v2/)
5. Production ready Health check endpoints via [Actuator](https://github.com/spring-projects/spring-boot/tree/v3.2.5/spring-boot-project/spring-boot-actuator)
6. Postman Collection for testing. See `bookstore/postman`.

## Contributing

1. Follow the [Google Java code style guidelines](https://google.github.io/styleguide/javaguide.html)
2. Set `SPRING_PROFILES_ACTIVE` to `dev`
3. Run the project at the `@SpringBootProject` entry point.

## API
Assuming a local development root url of `http://localhost:8080`
### Inventory Mangement
1. get-book GET `/inventory/{book_id}`
2. add-book POST `/inventory`
3. update-book PATCH `/inventory/{book_id}`
4. delete-book DELETE `/inventory/{book_id}`
5. get-books GET `/inventory?page={page_number}&title={book_title}&author={author}&size={page_size}`

### Bookstore Management (Cart / Checkout)
1. get-cart GET `/bookstore/{user_id}`
2. add-cart POST `/bookstore/{user_id}`
3. checkout-cart POST `/bookstore/{user_id}/checkout`
4. delete-from-cart DELETE `/bookstore/{user_id}`

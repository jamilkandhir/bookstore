import client from "./client";

export const getBooks = () => client.get("/books");

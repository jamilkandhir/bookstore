import client from "./client";


export const getCart = () => client.get("/cart");


export const addToCart = (item) => {
  return client.post("/cart", {
    book: { id: item.id },
    quantity: item.quantity
  });
};


export const removeFromCart = (id) => client.delete(`/cart/${id}`);

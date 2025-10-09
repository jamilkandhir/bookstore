import React from "react";
import Checkout from "../components/CheckoutForm";

function CheckoutPage() {
  const cartItems = [
    { id: 1, title: "My First Book", quantity: 2, price: 35.0 },
    { id: 2, title: "My Second Book", quantity: 1, price: 35.0 },
  ];

  return (
    <div>
      <h1>Checkout Page</h1>
      <Checkout cartItems={cartItems} />
    </div>
  );
}

export default CheckoutPage;

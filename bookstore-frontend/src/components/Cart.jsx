import React, { useState } from "react";
import "./Cart.css";
import { addToCart, removeFromCart } from "../api/cartApi";
import Checkout from "./CheckoutForm";

function Cart({ cartItems, onRemove, onClearCart }) {
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");
  const [showCheckout, setShowCheckout] = useState(false);

  const total = cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0);

  const handleCheckout = async () => {
    if (cartItems.length === 0) return;
    setLoading(true);
    setMessage("");

    try {
      await Promise.all(
        cartItems.map((item) => addToCart({ id: item.id, quantity: item.quantity }))
      );

      // Clear frontend cart
      onClearCart();

      // Show checkout page
      setShowCheckout(true);
    } catch (err) {
      console.error(err);
      setMessage("Checkout failed. Try again.");
    } finally {
      setLoading(false);
    }
  };

  if (showCheckout) {
    return <Checkout cartItems={cartItems} />;
  }

  return (
    <div className="cart-container">
      <h2>Shopping Cart</h2>
      {cartItems.length === 0 ? (
        <p>Your cart is empty</p>
      ) : (
        <div>
          <ul className="cart-list">
            {cartItems.map((item) => (
              <li key={item.id} className="cart-item">
                <span>
                  {item.title} (x{item.quantity})
                </span>
                <span>${(item.price * item.quantity).toFixed(2)}</span>
                <button onClick={() => onRemove(item.id)}>Remove</button>
              </li>
            ))}
          </ul>
          <h3>Total: ${total.toFixed(2)}</h3>

          {message && <p style={{ marginTop: "0.5rem" }}>{message}</p>}
        </div>
      )}
    </div>
  );
}

export default Cart;
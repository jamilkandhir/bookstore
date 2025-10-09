import React from "react";

function CheckoutForm({ cartItems }) {
  const total = cartItems.reduce((sum, item) => sum + item.price * item.quantity, 0);

  return (
    <div style={{ maxWidth: "600px", margin: "2rem auto", padding: "1rem", border: "1px solid #ccc", borderRadius: "8px" }}>
      <h2>Checkout</h2>
      {cartItems.length === 0 ? (
        <p>Your cart is empty.</p>
      ) : (
        <div>
          <ul style={{ listStyle: "none", padding: 0 }}>
            {cartItems.map((item) => (
              <li key={item.id} style={{ display: "flex", justifyContent: "space-between", marginBottom: "0.5rem" }}>
                <span>{item.title} (x{item.quantity})</span>
                <span>${(item.price * item.quantity).toFixed(2)}</span>
              </li>
            ))}
          </ul>
          <h3>Total: ${total.toFixed(2)}</h3>
        </div>
      )}
    </div>
  );
}

export default CheckoutForm;

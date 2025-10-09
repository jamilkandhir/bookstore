import React, { useEffect, useState } from "react";
import BookList from "../components/BookList";
import Cart from "../components/Cart";
import Checkout from "../components/CheckoutForm";
import { getBooks } from "../api/bookApi";

export default function Home() {
  const [books, setBooks] = useState([]);
  const [cart, setCart] = useState([]);
  const [showCheckout, setShowCheckout] = useState(false);

  useEffect(() => {
    async function loadBooks() {
      try {
        const response = await getBooks();
        setBooks(response.data);
      } catch (err) {
        console.error(err);
      }
    }
    loadBooks();
  }, []);

  const handleAdd = (bookId) => {
    const book = books.find((b) => b.id === bookId);
    setCart((prev) => {
      const existing = prev.find((item) => item.id === bookId);
      if (existing) {
        return prev.map((item) =>
          item.id === bookId ? { ...item, quantity: item.quantity + 1 } : item
        );
      } else {
        return [...prev, { ...book, quantity: 1 }];
      }
    });
  };

  const handleRemove = (bookId) => {
    setCart((prev) =>
      prev
        .map((item) =>
          item.id === bookId ? { ...item, quantity: item.quantity - 1 } : item
        )
        .filter((item) => item.quantity > 0)
    );
  };
   const handleClearCart = () => {
      setCart([]);
    };


  return (
    <div style={{ display: "flex", gap: "2rem", padding: "1rem" }}>
      <div style={{ flex: 2 }}>
        <BookList books={books} onAdd={handleAdd} />
      </div>
      <div style={{ flex: 1 }}>
         <Cart
                cartItems={cart}
                onRemove={handleRemove}
                onClearCart={handleClearCart}
              />
              {/* Checkout */}
                    {cart.length > 0 && !showCheckout && (
                      <button class="checkout-btn" onClick={() => setShowCheckout(true)} style={{ marginTop: "1rem" }}>
                        Proceed to Checkout
                      </button>
                    )}
                    {showCheckout && <Checkout cartItems={cart} />}
      </div>
    </div>
  );
}

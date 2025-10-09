import React from "react";
import "./BookList.css";

export default function BookList({ books, onAdd }) {
  return (
    <div className="booklist-container">
      <h2 className="booklist-title">Available Books</h2>
      {books.length === 0 ? (
        <p className="empty-message">No books available</p>
      ) : (
        <div className="book-grid">
          {books.map((book) => (
            <div key={book.id} className="book-card">
              <h3 className="book-title">{book.title}</h3>
              <p className="book-author">by {book.author}</p>
              <p className="book-price">${book.price.toFixed(2)}</p>
              <button
                className="add-btn"
                onClick={() => onAdd(book.id)}
              >
                Add to Cart
              </button>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

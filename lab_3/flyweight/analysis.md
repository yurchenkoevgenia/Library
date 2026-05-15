# Flyweight Demo Analysis

The flyweight example shares `BookType` objects between many `BookCopy` instances.

The shared state contains:
- title
- author
- genre
- publication year

The extrinsic state contains:
- copy id
- shelf location
- availability

The factory returns the same `BookType` object for identical book data, so repeated copies reuse shared objects instead of creating duplicates.

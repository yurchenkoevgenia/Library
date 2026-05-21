# Laboratory Work 3

## Composite

Composite is a structural pattern that lets you compose objects into tree structures and work with individual objects and groups of objects in the same way.

### Advantages

- uniform work with leaves and composites
- simple tree traversal
- easy extension of new component types

### Disadvantages

- can make the design more complex
- child management can be difficult in deep trees
- not suitable when a strict hierarchy is not needed

### When to use

- file systems
- menus
- catalogs
- nested organizational structures

## Decorator

Decorator is a structural pattern that adds new behavior to an object dynamically by placing it inside a wrapper object.

### Structure

- Component
- ConcreteComponent
- Decorator
- ConcreteDecorator

### When to use

- when behavior must be added without changing the original class
- when combinations of features are needed at runtime
- when inheritance would create too many subclasses

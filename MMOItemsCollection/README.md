# MMOItemsCollection 1.0.0

A fresh Paper plugin using MMOItems as its only item source. This version is a deterministic token shop/collection system, not a paid random loot-box system.

## Features
- MMOItems only
- `/collection` GUI
- Configurable MMOItems type + ID + token cost
- SQLite token storage
- `/collectionadmin give|take|reload`
- GitHub Actions build workflow

## Requirements
- Paper 1.21.x
- Java 21
- MMOItems installed

## Build
`mvn -B clean package`

Output: `target/MMOItemsCollection.jar`

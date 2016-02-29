# Mixxy

## Table of Contents

We list the things we talk about

## Introduction

We very quickly summarize what we're doing

### Purpose

We make a thing

### Scope

Discuss things we want to do, and things we *don't* want to do

### Definitions, acronyms, and abbreviations

Anything not obvious

### References

That IEEE document, I guess?

### Overview

Is this part even really necessary?  Introductions are supposed to be quick summaries; should I really have to read a list of definitions before I understand an introduction?

## Overall description

### Product perspective

Why are we doing this

### Product functions

What are we doing?

### User characteristics

Who is our target audience?

### Constraints

What gotchas should we consider?

### Assumptions and dependencies

What are we assuming actually exists or holds true?  (E.g. most of our users probably have up-to-date browsers)

## Specific requirements

### External interfaces

How will the user interact?

### Functions

Now we discuss things we actually have to do.  UI mockups will probably be dotted around here

#### Thing
  - List of things
  - Let's keep it concise

#### Other thing
  - This is text
  - I like words

### Performance requirements

Should we expect the user to go make a cup of coffee while waiting for something to happen?  (Hint: no)

### Logical database requirements

**Data Technology:** The data technology we will be using for this project will primarily be the GAE standard low-level Datastore Java API since it utilizes the Datastore to its full potential.

App Engine Datastore is a schemaless NoSQL datastore providing robust, scalable storage for your web application, with the following features:
- No planned downtime
- Atomic transactions
- High availability of reads and writes
- Strong consistency for reads and ancestor queries
- Eventual consistency for all other queries

**Stored Database Entities:** 

- **Users**: The user will be given attributes of all its account information such as: username, first name, last name, phone number, along with references to their comic creations and favorited comics. The User will also have a role attribute for escalated privilege that will allow them to carry their duties, such as: banning a user, suspending a user, deleting a remixed or original comic, if they are a moderator.

- **Comics**: Rendered raster images for the comic, original user that it was created by, and user that remixed the comic will be stored as a Comics Entity. We will be using DeviantArt Muro and most of these comics created by the users will be stored on DeviantArt. The comics created by non-DeviantArt users will be stored as a raster image in the database and will need a considerable amount of storage. 


### Design constraints

What restrictions (not conscious choices; I.e. don't say "we use Bootstrap") must we consider when designing this app?

### Software system attributes

What ultimately describes what Mixxy should be?  Yes, the usualy gamut of "reliable", "secure", etc. but remember, this is for people.  "Fun", "social", etc. are just as important

### Additional comments

I write more things

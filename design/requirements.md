# Mixxy

## Table of Contents

We list the things we talk about

## Introduction

We very quickly summarize what we're doing

### Purpose

The purpose of this document is to summarize a thing

### Scope

Discuss things we want to do, and things we *don't* want to do

### Definitions, acronyms, and abbreviations

**Work:** Any content a user has posted to Mixxy, typically an illustration.

**Remix:** The act of one user making a copy of another's work for the purpose of editing, improving upon, or otherwise modifying the original work.  (The original work is *not* modified in the process; remixing cannot be used for vandalism.)

**Content:** The subject matter of a work.

**18+:** Content which prominently features any of the following themes:

  - Explicit sexual themes (e.g. nudity or pornography)
  - Realistic violence and gore
  - Excessive strong language
  - Extreme treatment of controversial topics

*18+ content is fully permitted*, so long as it is categorized as such by its creator.

**Forbidden content:** Content that contains any of the following:

  - Child pornography
  - Copyright violations
  - Hateful speech and threats of violence
  - Links to any of the above

**Under no circumstances may a work with forbidden content be posted.  Forbidden content and any remixes thereof will be forcibly removed, and if necessary the relevant authorities will be contacted.**

### References

That IEEE document, I guess?

### Overview

Is this part even really necessary?  Introductions are supposed to be quick summaries; should I really have to read a list of definitions before I understand an introduction?

## Overall description

### Product perspective

With the advent of social media, much of digital culture now revolves around freely sharing--and occasionally modifying--user-made content.  Mixxy is designed with this trend in mind, to empower artists and illustrators of all skills to put their own twist on an existing work.  How this is applied is left entirely to the user base -- community-built stories, collaborative works, and even outright parody come to mind, though by no means should they be seen as limits.

### Product functions

More formally, Mixxy is an art platform where users can not only post and share their own work, but freely remix that of others.  Users may provide or remix content with either the provided in-browser image editor or through their own preferred toolset, uploading their work to Mixxy upon its completion.  Users can discuss works with one another, follow creators they admire, or find works based on a particular subject matter.

### User characteristics

Mixxy is designed primarily for the benefit of artists and their enthusiasts, with particular attention to the comic fanbase.  We expect that most potential users are young (typically ages 15-30) and reasonably tech-savvy.

Children under the age of 13 may not use Mixxy, due to legal requirements that we cannot meet pertaining to the Children's Online Privacy Protection Act.

### Constraints

The following issues arose in the design of Mixxy, all of which affect our options as developers:

- Content will need some form of moderation, in order to prevent Mixxy from being associated with illegal or unsavory material (e.g. child pornography).
- We cannot allow children under the age of 13 to use Mixxy, as we are not prepared to comply with COPPA.
- For legal reasons, all user works posted on Mixxy must be released under a license that permits free distribution and modification.
- We have no budget to speak of.
- As we are constrained to Google App Engine and Java 7, we may not be able to use certain libraries and tools.
- The in-browser editor must support raster image editing.  This implies that user works must be stored in raster format, resulting in higher storage and bandwidth requirements.

### Assumptions and dependencies

Any invalidation of these assumptions can affect the requirements.

- Our target audience likely has an up-to-date Web browser
- Our target audience is comfortable with other people being able to freely modify their own work

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

@bsabz this is your time to shine (ER diagrams will probably go here, too)

### Design constraints

What restrictions (not conscious choices; I.e. don't say "we use Bootstrap") must we consider when designing this app?

### Software system attributes

What ultimately describes what Mixxy should be?  Yes, the usualy gamut of "reliable", "secure", etc. but remember, this is for people.  "Fun", "social", etc. are just as important

### Additional comments

I write more things

# Mixxy

Developed by www.purple, consisting of:

- Jesse Talavera-Greenberg
- Brian Sabzjadid
- Kathleen Cleary
- Cristiano Miranda

## Table of Contents

[ To be generated and provided in another pull request. ]

## Introduction

The purpose of this document is to specify our plans for the development of Mixxy, a Web-based collaborative art platform.  Specifically, we discuss the relevance of such a service, the functionality we see as required in order to declare Mixxy a fulfilling experience, and the challenges we expect to encounter in the development process.

We opted for a holistic approach in the creation of this document; we give preference to simplicity and conciseness over strict conformance to a standard, expecting that a reader will be able to parse and understand this document in its entirety.  We also expect that the reader is able and willing to contact us regarding clarifications, and that this document will be updated accordingly to reflect them.

### Scope

This document is written to specify the following of Mixxy:

- Description of Mixxy's potential user appeal
- High-level requirements and constraints
- Summary of mission-critical data
- Use cases

The following are *not* within the scope of this document:

- Detailed information about data storage requirements
- Specification of algorithms or functionality in a formal syntax
- A detailed analysis of the target audience
- Business plan and monetization details
- Architecture, software design, or other implementation issues
- Technology choices for non-critical components (e.g. utility libraries, parsers)

### Definitions

**Guest (n.):** A person who browses Mixxy without being signed in.  May include users who have not yet signed in.

**User (n.):** A person who registers for an account with Mixxy and partakes in its services.  Does not necessarily contribute content (but we'd like them to).

**Moderator (n.):** A user who enforces community standards through disciplinary action.

**Comment (n.):** A user's contribution to the discussion of a work.

**Comment (v.):** The act of a user contributing to the discussion of a work.

**Work (n.):** Any content posted to Mixxy, typically an illustration.

**Flag (v.):** The act of a user reporting to a moderator a work, comment, or user profile that violates community standards.

**Ban (n.):** A revocation of a user's ability to post works or otherwise contribute to Mixxy, typically for violating the terms of service.  May be temporary or permanent, depending on the severity of the offense.

**Ban (v.):** The act of a moderator awarding a ban on a user who has violated the terms of service.

**Remix (v.):** The act of one user making a copy of another's work for the purpose of editing, improving upon, or otherwise modifying the original work.

**Remix (n.):** The result of a user's modifications to a work.

*When remixing a work, the original is not modified in the process.  Remixing cannot be used for vandalism.*

**18+ (adj.):** Prominent depiction of any of the following themes:

  - Explicit sexual themes (e.g. nudity or pornography)
  - Realistic violence and gore
  - Excessive strong language
  - Extreme treatment of controversial topics

*18+ content is fully permitted, so long as it is categorized as such by its creator.*  May also be referred to informally as "mature", "adult", or "NSFW" (not safe for work).

**Forbidden (adj.):** Any presence of the following themes:

  - Child pornography, bestiality, rape, and other illegal sexual activity
  - Violations of copyright and privacy
  - Hateful speech, harassment, and cyber-bullying
  - Threats of violence or other disruptive activity
  - Encouragement of criminal activity
  - External links to any of the above

*Under no circumstances may forbidden content be posted.  Forbidden content and any remixes thereof will be forcibly removed, and if necessary the relevant authorities will be contacted.*

## Overall description

### Rationale

With the advent of social media, much of digital culture now revolves around freely sharing --- and occasionally modifying --- user-made content.  Mixxy is designed with this trend in mind, to empower artists and illustrators of all skills to put their own twist on an existing work.  How this is applied is left entirely to the user base --- community-built stories, collaborative works, and even outright parody come to mind, though by no means should they be seen as limits.  Clever and emergent uses of the platform are highly encouraged.

More formally, Mixxy is an art platform where users can not only post and share their own work, but freely remix that of others.  Users may provide or remix content with either the provided in-browser image editor or through their own preferred toolset, uploading their work to Mixxy upon its completion.  Users can discuss works with one another, follow creators they admire, or find works based on a particular subject matter.

Mixxy is *not* designed as:

- A platform to exchange works for monetary value
- A static art gallery or portfolio Web page
- A conventional image sharing/hosting service or imageboard (e.g. Imgur, 4chan)
- A recruiting service for artists
- A repository of artwork for use in other media (e.g. clip art websites, Open Game Art)
- A tutorial service for art and illustration skills

Users are, however, welcome to use Mixxy for these purposes unless otherwise stated.

### User Interfaces

[ UML diagram to provide in another pull request ]

#### Sign In
|                     Attribute | Details |
|-------------------------------|---------|
|                         Actor | Guest
|                          Goal | To associate Mixxy activity with a given user.
|                 Preconditions | The User is not currently signed in.
|                       Trigger | Provide credentials to Mixxy.
|                         Steps | <ol><li>User starts the web application, which loads the homepage</li><li> User clicks on "Sign In" button</li></ol>|
|                    Exceptions | A user may provide invalid credentials.  In such a case, they are not signed in.
|                     Frequency | Frequent.  May be automated if the user's browser is configured as such.

#### Sign Out
|                     Attribute | Details |
|-------------------------------|---------|
|                         Actor | User
|                          Goal | To stop associating Mixxy activity with a given user.
|                 Preconditions | User is signed in.
|                       Trigger | Click "Sign Out" button, or the session expires.
|                         Steps | <ol><li> User clicks on the “Sign Out” button</li></ol>
|                    Exceptions | If the user is not currently signed in, they will be shown an error.  No further action shall be taken.
|                     Frequency | Frequent.  May be automated if the user's browser is configured as such.

#### Draw Work
|                     Attribute | Details |
|-------------------------------|---------|
|                         Actor | User
|                          Goal | Give users a creative outlet.
|                 Preconditions | User is currently signed in.
|                       Trigger | Click "Create New" button from Draw tab
|                      Scenario | <ol><li>User clicks "Create New" button from Draw menu</li><li>Image editor loads in browser, allowing user to create a work</li><li>Image is displayed in browser, user can publish work</li></ol>
|                    Exceptions | Created work is too large.
|                     Frequency | Moderate to Frequent

#### Upload Work
|                     Attribute | Details |
|-------------------------------|---------|
|                         Actor | User
|                          Goal | Allow users to create works with their preferred tools.
|                 Preconditions | User is signed in and able to upload work.
|                       Trigger | Click 'Upload' button from Draw tab
|                      Scenario | <ol><li>User clicks upload button from Draw menu</li><li>File chooser loads, prompting actor to select file to be uploaded</li><li>Image is displayed in browser, allows actor confirm via button click to add image to profile or cancel to return to Draw menu</li></ol>       
|                    Exceptions | Uploaded work is too large, or not in a known file format.
|                     Frequency | Moderate to Frequent

#### Edit Work
|                     Attribute | Details |
|-------------------------------|---------|
|                         Actor | User, Moderator
|                          Goal | Revise or improve a user's own existing work
|                 Preconditions | User is signed in and has previously posted works,
|                       Trigger | Click 'edit' link in sidebar of owned comic
|                      Scenario | <ol><li>Actor opens comic, clicks 'edit' in the sidebar</li><li>Comic is opened in Muro for editing</li><li>Image is displayed in browser, allows actor confirm via button click to add image to profile or cancel to return to Draw menu</li></ol>    
|                    Exceptions | None
|                     Frequency | Infrequently

#### Remix Work
|                     Attribute | Details |
|-------------------------------|---------|
|                         Actor | User
|                          Goal | Improve upon or otherwise creatively mutate existing works.
|                 Preconditions | User is signed in, and works available for remixing exist.
|                       Trigger | Click 'remix' link in sidebar of comic
|                      Scenario | <ol><li>Actor opens comic, clicks 'remix' in the sidebar</li><li>Comic is opened in Muro for remixing</li><li>Image is displayed in browser, allows actor confirm via button click to add image to profile or cancel to return to Draw menu</li></ol>    
|                    Exceptions | None
|                     Frequency | Moderate to Frequent

#### Delete Work
|                     Attribute | Details |
|-------------------------------|---------|
|                         Actor | User, Moderator
|                          Goal | Remove a work that no longer represents a user's skill level, or remove a work that violates the Terms of Service.
|                 Preconditions | User is signed in, and is either an administrator or has previously posted a work.
|                       Trigger | Click 'delete' link in sidebar of owned comic
|                      Scenario | <ol><li>Actor opens comic, clicks delete in the sidebar</li><li>Popup message appears asking actor to confirm deletion</li><li><ul><li>If actor confirms, comic is deleted</li><li>If actor clicks cancel, they are returned to comic</li></ul></li></ol>       
|                    Exceptions | None
|                     Frequency | Infrequently

#### Basic Search
|                     Attribute | Details |
|-------------------------------|---------|
|                         Actor | User
|                          Goal | Find a work given broad criteria
|                 Preconditions | None
|                       Trigger | User enters a single text string as search criteria
|                         Steps | <ol><li>Enter text string in search field</li><li>Navigate through results</li><li>Navigate to any desired works that fit the criterion</li></ol>
|                    Exceptions | No existing works may fit the requested criteria.  The user will be notified of this.
|                     Frequency | Frequently

#### Advanced Search
|                     Attribute | Details |
|-------------------------------|---------|
|                         Actor | User
|                          Goal | Find a work given multiple specific criteria
|                 Preconditions | None
|                       Trigger | User navigates to the advanced search page
|                         Steps | <ol><li>Provide criteria based on authorship, popularity, age, and tags, among other thing</li><li>Navigate through results</li><li>Navigate to any desired works that fit the criterion</li></ol>
|                    Exceptions | No existing works may fit the requested criteria.  The user will be notified of this.
|                     Frequency | Frequently

#### Register
|                     Attribute | Details |
|-------------------------------|---------|
|                         Actor | Guest
|                          Goal | Grant a user access to Mixxy's functionality.
|                 Preconditions | The user is not currently signed in.
|                       Trigger | The user clicks on the “Register” button
|                      Steps    | <ol><li>User starts the web application, which loads the homepage</li><li> User clicks on the “Register” button</li></ol>
|                    Exceptions | This button should always be enabled when the user is not logged in. Note that should a user already be logged in, the application shouldn't display this button.
|                     Frequency | Once

#### Tagging Works
|                     Attribute | Details |
|-------------------------------|---------|
|                 Primary actor | User, Moderator
|                          Goal | Categorize a work based on its content for easy retrieval
|                 Preconditions | Work in question must belong to user
|                       Trigger | User creates, remixes, or edits a work
|                         Steps | <ol><li>Enter delimited text strings that categorize the work</li><li>Finalize submission or update of work</li></ol>
|                    Exceptions | Certain tags may be reserved, or may only be used conditionally.  In such cases, the user may not post or update their work.
|                     Frequency | When creating or updating a work

#### Navigating to Similar Works
|                     Attribute | Details |
|-------------------------------|---------|
|                         Actor | Guest, User
|                          Goal | Find works with similar content that the user might enjoy.
|                 Preconditions | None
|                       Trigger | User navigates to related works near existing ones
|                         Steps | <ol><li>Given a small palette of similar works, navigate to any that are appealing.</li></ol>
|                    Exceptions | Not enough similar works may exist.  In practice, the definition of "similar" will be broadened, but in the early days there may simply not be enough works to go around.
|                     Frequency | Frequently

#### Ban User
|                     Attribute | Details |
|-------------------------------|---------|
|                         Actor | Moderator
|                          Goal | Enforce Mixxy's Terms of Service.
|                 Preconditions | The user being disciplined has violated the Terms of Service.
|                       Trigger | The admin clicks on a "ban/kick" button for a specific user.
|                         Steps | <ol><li>Admin starts the web application, which loads the homepage</li><li>Admin locates the user and clicks on the “ban/kick” button</li></ol>
|                    Exceptions | None, barring human error.
|                     Frequency | Infrequently

#### Reset Password
|                     Attribute | Details |
|-------------------------------|---------|
|                         Actor | User
|                          Goal | Help a user sign in again if they don't remember their credentials.
|                 Preconditions | The user exists, but is not currently signed in.
|                       Trigger | The user clicks on the "Forgot Password" button.
|                         Steps | <ol><li>User starts the web application, which loads the homepage</li><li> User locates the "Sign In" button which tkes them to the Sign In page.</li><li>The user enters their username or email and clicks on the "Forgot Password" button.</li></ol>
|                    Exceptions | This button should always be enabled when the user exists and is not logged in yet.
|                     Frequency | Infrequently

#### Post Comment
|                     Attribute | Details |
|-------------------------------|---------|
|                         Actor | User
|                          Goal | Discuss a work with other users.
|                 Preconditions | User is signed in and viewing a work.
|                       Trigger | User navigates to comment section of work.
|                         Steps | <ol><li>Click on comment text field.</li><li>Write comment.</li><li>Click on comment button.</li></ol>
|                    Exceptions | Comments disabled by work's author.
|                     Frequency | Frequently

#### Up Comment
|                     Attribute | Details |
|-------------------------------|---------|
|                         Actor | User
|                          Goal | Up a comment under a comic.
|                 Preconditions | User must be logged in, viewing a comic.
|                       Trigger | User navigates to comment section of comic.
|                         Steps | <ol><li>Navigate to comment.</li><li>Click Up button.</li></ol>
|                    Exceptions | Comments disabled by comic's author.
|                     Frequency | Frequently

#### Down Comment
|                     Attribute | Details |
|-------------------------------|---------|
|                         Actor | User
|                          Goal | Down a comment under a comic.
|                 Preconditions | User must be logged in, viewing a comic.
|                       Trigger | User navigates to comment section of comic.
|                         Steps | <ol><li>Navigate to comment.</li><li>Click Down button.</li></ol>
|                    Exceptions | Comments disabled by comic's author.
|                     Frequency | Frequently

#### Flag Comment
|                     Attribute | Details | 
|-------------------------------|---------|
|                         Actor | User
|                          Goal | Flag a comment under a comic.
|                 Preconditions | User must be logged in, viewing a comic.
|                       Trigger | User navigates to comment section of comic.
|                         Steps | <ol><li>Navigate to comment.</li><li>Click Flag button.</li></ol>
|                    Exceptions | Comments disabled by comic's author.
|                     Frequency | At will.

#### Like Comic
|                     Attribute | Details |
|-------------------------------|---------|
|                         Actor | User
|                          Goal | Express a user's appreciation for a work.
|                 Preconditions | User must be signed in.
|                       Trigger | User navigates to a comic.
|                         Steps | <ol><li>Navigate to comic.</li><li>Click on Like button.</li></ol>
|                    Exceptions | None.
|                     Frequency | Frequently

#### Flag Comic
|                     Attribute | Details |
|-------------------------------|---------|
|                         Actor | User
|                          Goal | Flag a comic with forbidden content.
|                 Preconditions | User must be logged in, viewing a commic.
|                       Trigger | User navigates to a comic.
|                         Steps | <ol><li>Navigate to comic.</li><li>Click on Flag button.</li></ol>
|                    Exceptions | User is not logged in.
|                     Frequency | Infrequently

#### Share Comic
|                     Attribute | Details | 
|-------------------------------|---------|
|                         Actor | Guest, User
|                          Goal | Share comic on social networks.
|                 Preconditions | User must be logged in, viewing a comic.
|                       Trigger | User navigates to a comic.
|                         Steps | <ol><li>Navigate to comic.</li><li>Click on Share button.</li><li>Pick social network to share to.</li></ol>
|                    Exceptions | User is not logged in.
|                     Frequency | Frequently

### Target Audience

Mixxy is designed primarily for the benefit of artists and their enthusiasts, with particular attention to the comic fanbase.  We expect that most potential users are young (typically ages 15-30) and reasonably tech-savvy.

Children under the age of 13 may not use Mixxy, because we do not have the resources to comply with the Children's Online Privacy Protection Act.

### Constraints

The following issues arose in the design of Mixxy, all of which affect our options as developers:

- Content will need some form of moderation, in order to prevent Mixxy from being associated with illegal or unsavory material (e.g. child pornography).
- We cannot allow children under the age of 13 to use Mixxy, as we are not prepared to comply with COPPA.
- For legal reasons, all user works posted on Mixxy must be released under a license that permits free distribution and modification.
- We have no budget to speak of.
- As we are constrained to Google App Engine and Java 7, we may not be able to use certain libraries and tools.
- Our use of DeviantArt Muro as an image editor tightly couples Mixxy to the service.
- The in-browser editor must support raster image editing.  This implies that user works must be stored in raster format, resulting in higher storage and bandwidth requirements.

### Assumptions and Dependencies

In designing Mixxy, we have assumed the following:

- Users will have a desktop or laptop computer
- Users will have a recent Web browser installed
  - Said browser will support modern Web standards such as HTML5, `<canvas>`, and the like.
- Users are comfortable with allowing other users to modify their work.
- Not all followers of a given user are themselves skilled artists.
- Users do not necessarily have accounts with other social platforms (e.g. DeviantArt).
- Users are vigilant enough to report any violation of Mixxy's code of conduct.

This document may require revision if any of the stated assumptions are violated.

## Specific Requirements

### External interfaces

The user will be able to remix the comics of their choosing on the web app via access from their laptop or desktop computers. They will be able to post, comment, like that comic, and if they so choose, they can subscribe to the user who post tasteful comics to receive a feed of their posts. The iOS app will be a compliment to the web app, where users get to view the comics they have remixed and other user content.

### Performance Requirements

Should we expect the user to go make a cup of coffee while waiting for something to happen?  (Hint: no)

### Logical database requirements

#### Data Technology

The data technology we will be using for this project will primarily be the GAE standard low-level Datastore Java API since it utilizes the Datastore to its full potential.

App Engine Datastore is a schemaless NoSQL datastore providing robust, scalable storage for your web application, with the following features:
- No planned downtime
- Atomic transactions
- High availability of reads and writes
- Strong consistency for reads and ancestor queries
- Eventual consistency for all other queries

#### Stored Database Entities

This section describes, on a high level, the most important entities that must be stored in the database.

##### Users

Users will have associated with them the typical details of account registration (e-mail, password, moderator permissions, etc.).  Alongside this, users will have associated with them the works they submit or remix, as well as a list of other uses they follow or works they like.

##### Works

Works are images stored in raster format.  Associated with them are the user that authored it, all derived remixes, and the users who like this work.  Of note is that our DeviantArt Muro integration allows us to store most (but not all) images off-site, on DeviantArt's servers.  Works created by those without a DeviantArt account can be stored on our servers directly.
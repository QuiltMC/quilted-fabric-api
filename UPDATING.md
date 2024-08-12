# Updating Quilted Fabric API

Quilted Fabric API is a quite interesting project, because it isn't any project, it is simultaneously a fork of Fabric API, a repackaging of Fabric API, a collection of bridges that connect Fabric's APIs to QSL, and a mixture of both!

On this document, the different processes of updating Quilted Fabric API and how they work will be explained here.


<!-- TODO: Fill this out more -->
## Guide
Always keep clean git history.
Make a branch to cherry-pick from. Make the needed changes at each commit.

<!-- Old Guide.
## Updating the Quilt Standard Libraries dependency

Quilted Fabric API is both a distributor of QSL (Quilt Standard Libraries) through the [QSL/QFAPI bundle](https://modrinth.com/mod/qsl) as well as an user of it. As mentioned before, Quilted Fabric API is also a collection of bridges between Fabric APIs and QSL equivalents, which means that an update isn't always straightforward!

When a QSL update is released, this is the cue to update it on Quilted Fabric API as well, due to it being the main distribution that both developers and users use. Do note that dependending of the circumstances, an update on QFAPI's side can be delayed if waiting for something else is beneficial for the developer's well-being.

In order to update? It's simple! Just update the version definition of the QSL dependency contained on `gradle.properties`. Depending of the update? That's all you need to do! But usually, the update may contain a new API that Fabric API might have an equivalent of, which does mean that Fabric's equivalent might need to be bridged to QSL's if it's necessary and feasible.

It is recommended to bridge all APIs that have an equivalent when that is possible, in order to reduce potential conflicts, allow for a bigger interoperability with QSL's APIs, as well as allow the QSL equivalent to be battle-tested through mods that do use Fabric API.

The bridging process really is up to the developer. We have Quilted Fabric API Base's `QuiltCompatEvent` utility class for directly bridging Fabric API and QSL events, something that sometimes can be a straightforward connection of dots, although it can be more than that on some cases, so do be aware of that!

Another important note is that your mileage may vary with each update. Some APIs will barely need work at all, but others might end with you architecturing a workaround like Quilted Fabric Content Registries' deferred queues! Do expect that some ports will require more effort than usual, but do note that once a bridge is built? It'll last for quite a long time!

## Syncing with upstream Fabric API

To be rewritten!

The process of syncing with the upstream can sometimes be a job so simple that all you need to do is fix the license headers and the gradle.properties conflict that pops up, but other times? It can be a quite complicated job that involves bridging major APIs in order to ensure intercompatibility.

The upstream sync process itself is one that in theory is simple, but due to the potential extra work needed, make sure to always consult with us on the toolchain discord!

The process is simple: you merge in the equivalent Fabric API branch into the Quilt one and fix all conflicts, as well as any potential trouble. FMJs that belong to main mods (not testmod ones) are to be translated into QMJ and the naming scheme is to be updated in order to have the `quilted_` prefix and in order to follow the snake cake convention.

A proper step-by-step tutorial will be written later, since it can involve Git knowledge!

## Porting to a new Minecraft version

The Minecraft update porting process is still one that we still have to refine ourselves. However, we still have some things set on stone.

Currently, the decision is that Quilted Fabric API ports are a low priority on the beginning of snapshot cycles, with the focus being getting QSL up-to-date with the latest snapshot, getting Quilt Mappings mapped, and maintaining the current maintained stable versions. This is to make life easier for the active maintainers, who already have to deal with many Quilt projects and has to deal with a QSL that is still growing up instead of being set in stone.

However, once a good point for porting has been found, progress on a Quilted Fabric API update should begin, with the priority being that ideally something should be done before the pre-releases begin.

### Porting

TBD
-->

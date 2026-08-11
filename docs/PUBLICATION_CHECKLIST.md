# Publication checklist

This checklist is for reviewing the repository before changing its visibility to public.

- [ ] No real database credentials, API keys, access tokens, or private keys are committed.
- [ ] The previously embedded Google Maps API key has been rotated or revoked.
- [ ] No runtime-generated `.webm` recordings are committed.
- [ ] No real location history, personal email addresses, or other personal data is included in the demo SQL.
- [ ] Build output (`build/`, `target/`, `.class`, `.war`) is not tracked.
- [ ] Team members have agreed to publication of the team-authored source code.
- [ ] Permission to redistribute UI images/assets has been confirmed.
- [ ] Redistribution terms for the JAR files under `WEB-INF/lib/` have been checked, or the binaries have been replaced with dependency-manager declarations.
- [ ] The repository README accurately distinguishes individual contributions from team work.

## Source attribution cleanup in V2

The training-provided `DBManager.java` that contained an NPO-AIP copyright notice and another author's attribution was not published by merely deleting its header. It was replaced with a small independently written JDBC connection helper that reads configuration from environment variables.

A text scan of this V2 copy found no remaining occurrences of `NPO-AIP`, `K.Imazawa`, `Copyright`, or `@author` in the project source files at the time the archive was prepared.

This checklist is a repository-hygiene aid, not a determination of ownership or redistribution rights for team/course assets.

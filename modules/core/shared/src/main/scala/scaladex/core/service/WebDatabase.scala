package scaladex.core.service

import java.util.UUID

import scala.concurrent.Future

import scaladex.core.model._

trait WebDatabase {
  // artifacts
  def insertArtifact(artifact: Artifact): Future[Boolean]
  def getArtifacts(groupId: Artifact.GroupId, artifactId: String): Future[Seq[Artifact]]
  def getArtifacts(projectRef: Project.Reference): Future[Seq[Artifact]]
  def getArtifacts(ref: Project.Reference, artifactName: Artifact.Name, stableOnly: Boolean): Future[Seq[Artifact]]
  def getArtifacts(ref: Project.Reference, artifactName: Artifact.Name, version: SemanticVersion): Future[Seq[Artifact]]
  def getArtifactsByName(projectRef: Project.Reference, artifactName: Artifact.Name): Future[Seq[Artifact]]
  def getLatestArtifacts(ref: Project.Reference): Future[Seq[Artifact]]
  def getArtifactByMavenReference(mavenRef: Artifact.MavenReference): Future[Option[Artifact]]
  def getAllArtifacts(language: Option[Language], platform: Option[Platform]): Future[Seq[Artifact]]
  def countArtifacts(): Future[Long]

  // artifact dependencies
  def getDirectDependencies(artifact: Artifact): Future[Seq[ArtifactDependency.Direct]]
  def getReverseDependencies(artifact: Artifact): Future[Seq[ArtifactDependency.Reverse]]

  // projects
  def insertProjectRef(ref: Project.Reference, status: GithubStatus): Future[Boolean]
  def updateProjectSettings(ref: Project.Reference, settings: Project.Settings): Future[Unit]
  def getAllProjectsStatuses(): Future[Map[Project.Reference, GithubStatus]]
  def getProject(projectRef: Project.Reference): Future[Option[Project]]
  def getFormerReferences(projectRef: Project.Reference): Future[Seq[Project.Reference]]
  def countVersions(ref: Project.Reference): Future[Long]

  // Github info and status
  def updateGithubInfoAndStatus(ref: Project.Reference, info: GithubInfo, status: GithubStatus): Future[Unit]
  def updateGithubStatus(ref: Project.Reference, status: GithubStatus): Future[Unit]
  def moveProject(ref: Project.Reference, info: GithubInfo, status: GithubStatus.Moved): Future[Unit]

  // project dependencies
  def countProjectDependents(projectRef: Project.Reference): Future[Long]
  def getProjectDependencies(ref: Project.Reference, version: SemanticVersion): Future[Seq[ProjectDependency]]
  def getProjectDependents(ref: Project.Reference): Future[Seq[ProjectDependency]]

  // users
  def insertUser(userId: UUID, user: UserInfo): Future[Unit]
  def updateUser(userId: UUID, userState: UserState): Future[Unit]
  def getUser(userId: UUID): Future[Option[UserState]]
  def getAllUsers(): Future[Seq[(UUID, UserInfo)]]
  def deleteUser(userId: UUID): Future[Unit]
}

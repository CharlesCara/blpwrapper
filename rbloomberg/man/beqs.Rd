\name{beqs}
\alias{beqs}
\title{Get Bloomberg Equity Screen Data}
\description{
  This is the primary user-level function for retrieving Bloomberg Equity Screen data created using the EQS function in Bloomberg.
}
\usage{
blp(conn, securities, fields, start_date, end_date = NULL, override_fields = NULL, overrides NULL)
}
\arguments{
  \item{conn}{Connection object}
  \item{screenName}{The name of the Equity Screen listed on EQS.}
  \item{screenType}{string with the location of the screen either PRIVATE for user defined screen or GLOBAL for a Bloomberg EQS screen.}
  \item{languageId}{string with the language eg ENGLISH, KANJI, FRENCH, GERMAN, SPANISH, PORTUGUESE, ITALIAN, CHINESE_TRA, KOREAN, CHINESE_SIM, THAI, SWED, FINNISH, DUTCH, MALAY, RUSSIAN, GREEK, POLISH, DANISH, FLEMISH, ESTONIAN, TURKISH, NORWEGIAN, LATVIAN, LITHUANIAN, INDONESIAN }
  \item{Group}{Screen folder name as defined in EQS.}
  \item{asOfDate}{As Of date for data retrieved, either as a YYYYMMDD format string or a date object of any class which responds correctly to format().}
}
\details{
an equivalent to the beqs() function in Bloomberg Excel.  Returns as a dataframe with the contents of an Equity Screen set up using the EQS page on Bloomberg.  The data can be as of a date.
}
\keyword{math}
\examples{
# Please consult unit tests for more examples.
\dontrun{
library(RBloomberg)
conn <- blpConnect()

beqs(conn, "Global Volume Surges", "GLOBAL")


blpDisconnect(conn)

}
}
\author{Charles Cara \email{charles.cara@absolute-strategy.com}}


<%@ page
  contentType="text/html; charset=UTF-8"
  import="javax.servlet.*"
  import="javax.servlet.http.*"
  import="java.io.*"
  import="java.net.URL"
  import="org.apache.hadoop.mapred.*"
  import="org.apache.hadoop.mapred.JSPUtil.JobWithViewAccessCheck"
  import="org.apache.hadoop.util.*"
%>
<%!	private static final long serialVersionUID = 1L;
%>


<%
  final JobTracker tracker = (JobTracker) application.getAttribute("job.tracker");
  final String jobId = request.getParameter("jobid");
  if (jobId == null) {
    out.println("<h2>Missing 'jobid' for fetching job configuration!</h2>");
    return;
  }
%>
  
<html>

<title>Job Configuration: JobId - <%= jobId %></title>

<body>
<h2>Job Configuration: JobId - <%= jobId %></h2><br>

<%
  JobWithViewAccessCheck myJob = JSPUtil.checkAccessAndGetJob(tracker,
      JobID.forName(jobId), request, response);
  if (!myJob.isViewJobAllowed()) {
    return; // user is not authorized to view this job
  }

  JobInProgress job = myJob.getJob();
  if (job == null) {
    out.print("<b>Job " + jobId + " not found.</b><br>\n");
    return;
  }

  String jobFilePath = JobTracker.getLocalJobFilePath(JobID.forName(jobId));
  FileInputStream jobFile = null;
  try {
    jobFile = new FileInputStream(jobFilePath);
    JobConf jobConf = new JobConf(jobFilePath);
    XMLUtils.transform(
        jobConf.getConfResourceAsInputStream("webapps/static/jobconf.xsl"),
        jobFile, out);
  } catch (Exception e) {
    out.println("Failed to retreive job configuration for job '" + jobId + "!");
    out.println(e);
  } finally {
    if (jobFile != null) {
      try { 
        jobFile.close(); 
      } catch (IOException e) {}
    }
  }
%>

<br>
<%
out.println(ServletUtil.htmlFooter());
%>

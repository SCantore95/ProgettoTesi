package tesi.example.myapplication.fragment;

public class ResultsItem {
    String description;
    String destinationIPs;
    String events;
    String logSources;
    String magnitude;
    String offenceSource;
    String offenseType;
    String sourceIPs;
    String users;
    String id;
    String startDate;
    String flows;
    String lastEventsFlow;


    public ResultsItem(String description, String destinationIPs, String events, String logSources, String magnitude,
                       String offenceSource, String offenseType, String sourceIPs, String users, String id,
                       String startDate,String flows,String lastEventsFlow) {
        this.description = description;
        this.destinationIPs = destinationIPs;
        this.events = events;
        this.logSources = logSources;
        this.magnitude = magnitude;
        this.offenceSource = offenceSource;
        this.offenseType = offenseType;
        this.sourceIPs = sourceIPs;
        this.users = users;
        this.id = id;
        this.startDate=startDate;
        this.flows=flows;
        this.lastEventsFlow=lastEventsFlow;

    }

    public ResultsItem() {

    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDestinationIPs() {
        return destinationIPs;
    }

    public void setDestinationIPs(String destinationIPs) {
        this.destinationIPs = destinationIPs;
    }

    public String getEvents() {
        return events;
    }

    public void setEvents(String events) {
        this.events = events;
    }

    public String getLogSources() {
        return logSources;
    }

    public void setLogSources(String logSources) {
        this.logSources = logSources;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
    }

    public String getOffenceSource() {
        return offenceSource;
    }

    public void setOffenceSource(String offenceSource) {this.offenceSource = offenceSource;}


    public String getOffenseType() {return offenseType;}

    public void setOffenseType(String offenseType) {this.offenseType = offenseType;}

    public String getSourceIPs() {return sourceIPs;}

    public void setSourceIPs(String sourceIPs) {this.sourceIPs = sourceIPs;}

    public String getUsers() {return users;}

    public void setUsers(String users) {this.users = users;}

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}
    public String getStartDate() {return startDate;}

    public void setStartDate(String startDate) {this.startDate = startDate;}
    public String getFlows() {return flows;}

    public void setFlows(String flows) {this.flows = flows;}
    public String getLastEventsFlow() {return lastEventsFlow;}

    public void setLastEventsFlow(String lastEventsFlow) {this.lastEventsFlow = lastEventsFlow;}


}


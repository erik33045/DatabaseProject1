package com.company;

import java.io.*;
import java.util.ArrayList;

public class P1 {

    //This object represents the Team tuple in the Database. This class contains the properties and methods
    //associated with Teams.
    public class Team {
        //Capital Letters Or Digits
        public String team_ID;
        //One or two Words
        public String location;
        //A string, no validation
        public String name;
        //An upper case letter
        public char league;

        //Method to Make sure that the team we pulled was valid. This will be called before we save to the DB.
        public String IsTeamValid() {
            boolean errorHasOccurred = false;
            String ErrorString = "The Following Errors Occurred: ";

            //If the ascii value of the character is not a capital letter, league isn't valid.
            if (this.league < 65 || this.league > 90) {
                errorHasOccurred = true;
                ErrorString = ErrorString.concat("\n League is not a capital letter. ");
            }

            //If the TeamId is not capital letters or numbers, it isn't valid
            for (int i = 0; i < this.team_ID.length(); i++) {
                //If not between A-Z or between 0-9, its not valid
                if (!((this.team_ID.charAt(i) >= 48 && this.team_ID.charAt(i) <= 57) || (this.team_ID.charAt(i) >= 65 && this.team_ID.charAt(i) <= 90))) {
                    errorHasOccurred = true;
                    ErrorString = ErrorString.concat("\n Team_ID must consist of Capital letters or digits.");
                    break;
                }
            }

            //A few rules here:
            //  May contain one space
            //  Needs to be Capital or Lower case letter
            boolean spaceUsed = false;
            boolean needToBreak = false;
            for (int i = 0; i < this.location.length(); i++) {
                if (this.location.charAt(i) == 32) {
                    if (!spaceUsed)
                        spaceUsed = true;
                    else {
                        errorHasOccurred = true;
                        ErrorString = ErrorString.concat("\n Location may contain only one space.");
                        needToBreak = true;
                    }
                } else {
                    //If not between A-Z or between a-z, it's not valid
                    if (!((this.location.charAt(i) >= 65 && this.location.charAt(i) <= 90) || (this.location.charAt(i) >= 97 && this.location.charAt(i) <= 122))) {
                        errorHasOccurred = true;
                        ErrorString = ErrorString.concat("\n Location may contain upper or lower case letters or one space.");
                        needToBreak = true;
                    }
                }

                if (needToBreak)
                    break;
            }//End For

            //If error has occurred, return the string. Else, return an empty string.
            return errorHasOccurred ? ErrorString : "";
        }//End IsValid Method

        //This method will parse the string for commas, and then Create a team Object based upon the comma separated values
        public Team GetTeamFromString(String teamString) {
            try {
                //This will be the array of strings we populate to our new object.
                String[] StringArray = teamString.split(",");
                return CreateTeamFromStringArray(StringArray);


            } catch (Exception e) {
                return null;
            }
        }

        public Team CreateTeamFromStringArray(String[] stringArray) throws Exception {
            //Sanity check. I need four values, no more no less.
            if (stringArray.length != 4)
                throw new Exception("The Expected Number of values was 4 but found " + stringArray.length);

            Team returnTeam = new Team();

            //Populate the Team Object, remove the last comma
            returnTeam.team_ID = stringArray[0];
            returnTeam.location = stringArray[1];
            returnTeam.name = stringArray[2];
            returnTeam.league = stringArray[3].charAt(0);
            return returnTeam;
        }

        //Get a list of teams from the DB. If city is supplied, then only get those matching the city.
        public ArrayList<Team> GetTeams(String city) {
            ArrayList<Team> returnList = new ArrayList<Team>();
            try {
                BufferedReader br = new BufferedReader(new FileReader("teams.txt"));
                String line = br.readLine();
                Team temp = new Team();
                while (line != null) {
                    if ((city.equals(""))) {
                        returnList.add(temp.GetTeamFromString(line));
                    } else if (temp.GetTeamFromString((line)).location.equals(city))
                        returnList.add(temp.GetTeamFromString(line));

                    line = br.readLine();
                }
                br.close();
                return returnList;
            } catch (Exception e) {
                return null;
            }
        }

    }//End Team Class

	/* Define data structures for holding the data here */

    public P1() {

    }

    public void run() {
        CommandParser parser = new CommandParser();
        System.out.println("The mini-DB of NBA coaches and teams");
        System.out.println("Please enter a command.  Enter 'help' for a list of commands.");
        System.out.println();
        System.out.print("> ");

        Command cmd;
        while ((cmd = parser.fetchCommand()) != null) {

            boolean result = false;
            String error = "";
            if (cmd.getCommand().equals("help")) {
                result = doHelp();

		/* You need to implement all the following commands */
            } else if (cmd.getCommand().equals("add_coach")) {

            } else if (cmd.getCommand().equals("add_team")) {
                try {
                    String[] parameters = cmd.getParameters();

                    //Need four parameters to Add a Team
                    if (parameters.length != 4) {
                        result = false;
                        error = "Invalid Number of parameters. Expected 4.";
                    } else {
                        //Before we pass the parameters to the creation function, replace any instances of + with a space
                        parameters[1] = parameters[1].replace("+", " ");

                        Team temp = new Team();
                        Team teamToAdd = temp.CreateTeamFromStringArray(parameters);

                        String errorMessage = teamToAdd.IsTeamValid();

                        //Make sure the team is valid. If not, throw an error.
                        if (!errorMessage.equals("")) {
                            result = false;
                            error = errorMessage;
                        } else {
                            //Write to the file
                            AddTeam(teamToAdd);
                            System.out.println("Team Added Successfully.");
                        }
                    }
                } catch (Exception e) {
                    result = false;
                    error = e.getMessage();
                }
            } else if (cmd.getCommand().equals("print_coaches")) {

            } else if (cmd.getCommand().equals("print_teams")) {
                try {
                    //Print the list of Teams
                    PrintTeams();
                } catch (Exception ex) {
                    result = false;
                    error = ex.getMessage();
                }


            } else if (cmd.getCommand().equals("coaches_by_name")) {

            } else if (cmd.getCommand().equals("teams_by_city")) {
                try {
                    String[] parameters = cmd.getParameters();
                    if (parameters.length != 1) {
                        result = false;
                        error = "Invalid Number of parameters. Expected 1.";
                    } else {
                        TeamsByCity(parameters[0]);
                    }
                } catch (Exception ex) {
                    result = false;
                    error = ex.getMessage();
                }


            } else if (cmd.getCommand().equals("load_coaches")) {

            } else if (cmd.getCommand().equals("load_teams")) {

            } else if (cmd.getCommand().equals("best_coach")) {

            } else if (cmd.getCommand().equals("exit")) {
                System.out.println("Leaving the database, goodbye!");
                break;
            } else if (cmd.getCommand().equals("")) {
            } else {
                System.out.println("Invalid Command, try again!");
            }

            if (!result) System.out.println(error);
            System.out.print("> ");
        }
    }

    //Method to append a team to the teams.txt file
    private void AddTeam(Team teamToAdd) throws IOException {
        BufferedWriter output;
        output = new BufferedWriter(new FileWriter("teams.txt", true));
        output.newLine();
        output.append(teamToAdd.team_ID).append(",").append(teamToAdd.location).append(",").append(teamToAdd.name).append(",").append(teamToAdd.league);
        output.close();
    }

    //Method to print all Teams
    private void PrintTeams() {
        Team temp = new Team();
        ArrayList<Team> teams = temp.GetTeams("");

        //Print the top line
        System.out.println("Printing List of Teams: ");

        if (teams.size() > 1) {
            //Print each Team
            for (int i = 1; i < teams.size(); i++) {
                System.out.println("\t" + teams.get(i).team_ID + ", " + teams.get(i).location + ", " + teams.get(i).name + ", " + teams.get(i).league);
            }

        }

    }

    //Method which Prints out the teams by a given city parameter
    private void TeamsByCity(String parameter) {
        Team temp = new Team();
        ArrayList<Team> teams = temp.GetTeams(parameter.replace('+', ' '));

        //Print the Top Line
        System.out.println("Printing List of Teams matching City \"" + parameter.replace('+', ' ') + "\":");

        if (teams.size() > 1) {
            //Print each Team
            for (Team team : teams) {
                System.out.println("\t" + team.team_ID + ", " + team.location + ", " + team.name + ", " + team.league);
            }

        }
    }

    //Method that displays help text
    private boolean doHelp() {
        System.out.println("add_coach ID SEASON FIRST_NAME LAST_NAME SEASON_WIN ");
        System.out.println("          SEASON_LOSS PLAYOFF_WIN PLAYOFF_LOSS TEAM - add new coach data");
        System.out.println("add_team ID LOCATION NAME LEAGUE - add a new team");
        System.out.println("print_coaches - print a listing of all coaches");
        System.out.println("print_teams - print a listing of all teams");
        System.out.println("coaches_by_name NAME - list info of coaches with the specified name");
        System.out.println("teams_by_city CITY - list the teams in the specified city");
        System.out.println("load_coach FILENAME - bulk load of coach info from a file");
        System.out.println("load_team FILENAME - bulk load of team info from a file");
        System.out.println("best_coach SEASON - print the name of the coach with the most net wins in a specified season");
        System.out.println("search_coaches field=VALUE - print the name of the coach satisfying the specified conditions");
        System.out.println("delete_coaches field=VALUE - delete the coach satisfying the specified conditions");
        System.out.println("exit - quit the program");
        return true;
    }

    /**
     * @param args
     */
}

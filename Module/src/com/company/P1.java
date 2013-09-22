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

        //Method to create a Team object from a provided string array
        public Team CreateTeamFromStringArray(String[] stringArray) throws Exception {
            //Sanity check. I need four values, no more no less.
            if (stringArray.length != 4)
                throw new Exception("The Expected Number of values was 4 but found " + stringArray.length);

            Team returnTeam = new Team();

            //Populate the Team Object
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

                //This is the first line in the table. It names the columns.
                //noinspection UnusedAssignment
                String line = br.readLine();

                line = br.readLine();

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

    //This object represents the Coach tuple in the Database. This class contains properties and methods
    //associated with Coaches
    public class Coach {
        //String: less than 7 cap letters and 2 digits
        public String coach_ID;
        //int: 4 digits
        public int season;
        //Int: Positive, 1 digit
        public int yr_order;
        //String: no space
        public String first_name;
        //String: may have one space
        public String last_name;
        //int: greater than 0
        public int season_win;
        //int: greater than 0
        public int season_loss;
        //int: greater than 0
        public int playoff_win;
        //int: greater than 0
        public int playoff_loss;
        //String: Capital Letters or digits.
        public String team;

        public String IsCoachValid() {
            return "";
        }

        //Method to create a coach object from a provided string array
        public Coach CreateCoachFromStringArray(String[] stringArray) throws Exception {
            //Sanity check. I need ten values, no more no less.
            if (stringArray.length != 10)
                throw new Exception("The Expected Number of values was 10 but found " + stringArray.length);

            Coach returnCoach = new Coach();

            //Populate the Coach Object
            returnCoach.coach_ID = stringArray[0];
            returnCoach.season = Integer.parseInt(stringArray[1]);
            returnCoach.yr_order = Integer.parseInt(stringArray[2]);
            returnCoach.first_name = stringArray[3];
            returnCoach.last_name = stringArray[4];
            returnCoach.season_win = Integer.parseInt((stringArray[5]));
            returnCoach.season_loss = Integer.parseInt((stringArray[6]));
            returnCoach.playoff_win = Integer.parseInt((stringArray[7]));
            returnCoach.playoff_loss = Integer.parseInt((stringArray[8]));
            returnCoach.team = stringArray[9];

            return returnCoach;
        }

        //This method will parse the string for commas, and then Create a Coach Object based upon the comma separated values
        public Coach GetCoachFromString(String coachString) {
            try {
                //This will be the array of strings we populate to our new object.
                String[] StringArray = coachString.split(",");
                return CreateCoachFromStringArray(StringArray);


            } catch (Exception e) {
                return null;
            }
        }

        //Get a list of coaches from the DB. If last name is supplied, then only get those matching the last name.
        public ArrayList<Coach> GetCoaches(String lastName) {
            ArrayList<Coach> returnList = new ArrayList<Coach>();
            try {
                BufferedReader br = new BufferedReader(new FileReader("coaches_season.txt"));

                //This is the first line in the table. It names the columns.
                //noinspection UnusedAssignment
                String line = br.readLine();

                line = br.readLine();

                Coach temp = new Coach();
                while (line != null) {
                    if ((lastName.equals(""))) {
                        returnList.add(temp.GetCoachFromString(line));
                    } else if (temp.GetCoachFromString((line)).last_name.equals(lastName))
                        returnList.add(temp.GetCoachFromString(line));

                    line = br.readLine();
                }
                br.close();
                return returnList;
            } catch (Exception e) {
                return null;
            }
        }
    }

	/* Define data structures for holding the data here */

    public P1() {

    }

    @SuppressWarnings("ConstantConditions")
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
                try {
                    String[] parameters = cmd.getParameters();

                    //Need four parameters to Add a Coach
                    if (parameters.length != 10) {
                        result = false;
                        error = "Invalid Number of parameters. Expected 10.";
                    } else {
                        //Before we pass the parameters to the creation function, replace any instances of + with a space
                        parameters[3] = parameters[3].replace("+", " ");

                        Coach temp = new Coach();
                        Coach coachToAdd = temp.CreateCoachFromStringArray(parameters);

                        String errorMessage = coachToAdd.IsCoachValid();

                        //Make sure the team is valid. If not, throw an error.
                        if (!errorMessage.equals("")) {
                            result = false;
                            error = errorMessage;
                        } else {
                            //Write to the file
                            AddCoach(coachToAdd);
                            System.out.println("Coach Added Successfully.");
                        }
                    }
                } catch (Exception e) {
                    result = false;
                    error = e.getMessage();
                }
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
                try {
                    //Print the list of Coaches
                    PrintCoaches();
                } catch (Exception ex) {
                    result = false;
                    error = ex.getMessage();
                }
            } else if (cmd.getCommand().equals("print_teams")) {
                try {
                    //Print the list of Teams
                    PrintTeams();
                } catch (Exception ex) {
                    result = false;
                    error = ex.getMessage();
                }
            } else if (cmd.getCommand().equals("coaches_by_name")) {
                try {
                    String[] parameters = cmd.getParameters();
                    if (parameters.length != 1) {
                        result = false;
                        error = "Invalid Number of parameters. Expected 1.";
                    } else {
                        CoachesByLastName(parameters[0]);
                    }
                } catch (Exception ex) {
                    result = false;
                    error = ex.getMessage();
                }
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
                result = true;
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
        output.append(teamToAdd.team_ID).append(",")
                .append(teamToAdd.location).append(",")
                .append(teamToAdd.name).append(",")
                .append(teamToAdd.league);
        output.close();
    }

    //Method to append a coach to the coaches_season.txt file
    @SuppressWarnings("StringConcatenationInsideStringBufferAppend")
    private void AddCoach(Coach coachToAdd) throws IOException {
        BufferedWriter output;
        output = new BufferedWriter(new FileWriter("coaches_season.txt", true));
        output.newLine();
        output.append(coachToAdd.coach_ID).append(",")
                .append(coachToAdd.season + "").append(",")
                .append(coachToAdd.first_name).append(",")
                .append(coachToAdd.last_name).append(",")
                .append(coachToAdd.season_win + "").append(",")
                .append(coachToAdd.season_loss + "").append(",")
                .append(coachToAdd.playoff_win + "").append(",")
                .append(coachToAdd.playoff_loss + "").append(",")
                .append(coachToAdd.team);
        output.close();
    }

    //Method to print all Teams
    private void PrintTeams() {
        Team temp = new Team();
        ArrayList<Team> teams = temp.GetTeams("");

        //Print the top line
        System.out.println("Printing List of Teams: ");

        if (teams.size() > 1) {
            PrintListOfTeams(teams);
        }
    }

    private void PrintListOfTeams(ArrayList<Team> teams) {
        //Print each Team
        for (Team team : teams) {
            System.out.println("\t" + team.team_ID + ", "
                    + team.location + ", "
                    + team.name + ", "
                    + team.league);
        }
    }

    //Method to print all Coaches
    private void PrintCoaches() {
        Coach temp = new Coach();
        ArrayList<Coach> coaches = temp.GetCoaches("");

        //Print the top line
        System.out.println("Printing List of Coaches: ");

        if (coaches.size() > 1) {
            PrintListOfCoaches(coaches);

        }
    }

    //Common method to print out a list of coaches.
    private void PrintListOfCoaches(ArrayList<Coach> coaches) {
        //Print each Coach
        for (Coach coach : coaches) {
            System.out.println
                    ("\t" + coach.coach_ID + ", "
                            + coach.season + ", "
                            + coach.yr_order + ", "
                            + coach.first_name + ", "
                            + coach.last_name + ", "
                            + coach.season_win + ", "
                            + coach.season_loss + ", "
                            + coach.playoff_win + ", "
                            + coach.playoff_loss + ", "
                            + coach.team
                    );
        }
    }

    //Method which Prints out the teams by a given city parameter
    private void TeamsByCity(String parameter) {
        Team temp = new Team();
        ArrayList<Team> teams = temp.GetTeams(parameter.replace('+', ' '));

        //Print the Top Line
        System.out.println("Printing List of Teams matching City \"" + parameter.replace('+', ' ') + "\":");

        if (teams.size() >= 1) {
            //Print each Team
            PrintListOfTeams(teams);
        }
    }

    //Method which Prints out the coaches by a given lastName parameter
    private void CoachesByLastName(String parameter) {
        Coach temp = new Coach();
        ArrayList<Coach> coaches = temp.GetCoaches(parameter.replace('+', ' '));

        //Print the Top Line
        System.out.println("Printing List of Coaches matching Last Name \"" + parameter.replace('+', ' ') + "\":");

        if (coaches.size() >= 1) {
            //Print each Team
            PrintListOfCoaches(coaches);
        }
    }

    //Method that displays help text
    @SuppressWarnings("SameReturnValue")
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

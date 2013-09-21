package com.company;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
public class P1 {

    //This object represents the Team tuple in the Database. This class contains the properties and methods
    //associated with Teams.
    public class Team
    {
        //Capital Letters Or Digits
        public String team_ID;
        //One or two Words
        public String location;
        //A string, no validation
        public String name;
        //An upper case letter
        public char league;

        //Method to Make sure that the team we pulled was valid. This will be called before we save to the DB.
        public String IsTeamValid()
        {
            boolean errorHasOccurred = false;
            String ErrorString = "The Following Errors Occurred: ";

            //If the ascii value of the character is not a capital letter, league isn't valid.
            if(this.league < 65 || this.league > 90)
            {
                errorHasOccurred = true;
                ErrorString = ErrorString.concat("\n League is not a capital letter. ");
            }

            //If the TeamId is not capital letters or numbers, it isn't valid
            for(int i = 0; i<this.team_ID.length(); i++)
            {
                if((this.team_ID.charAt(i) < 48 || this.team_ID.charAt(i) > 57) || (this.team_ID.charAt(i) < 65 || this.team_ID.charAt(i) > 90))
                {
                    errorHasOccurred = true;
                    ErrorString =ErrorString.concat("\n Team_ID must consist of Capital letters or digits.");
                    break;
                }
            }

            //A few rules here:
            //  May contain one space
            //  Needs to be Capital or Lower case letter
            boolean spaceUsed = false;
            boolean needToBreak = false;
            for(int i = 0; i<this.location.length(); i++)
            {
                if(this.location.charAt(i) == 32)
                {
                    if(!spaceUsed)
                        spaceUsed = true;
                    else
                    {
                        errorHasOccurred = true;
                        ErrorString =ErrorString.concat("\n Location may contain only one space.");
                        needToBreak = true;
                    }
                }
                else
                {
                    if((this.team_ID.charAt(i) < 65 || this.team_ID.charAt(i) > 90) || (this.team_ID.charAt(i) < 97 || this.team_ID.charAt(i) > 122))
                    {
                        errorHasOccurred = true;
                        ErrorString = ErrorString.concat("\n Location may contain upper or lower case letters or one space.");
                        needToBreak = true;
                    }
                }

                if(needToBreak)
                    break;
            }//End For

            //If error has occured, return the string. Else, return an empty string.
            return errorHasOccurred ? ErrorString : "";
        }//End IsValid Method

        //This method will parse the string for commas, and then Create a team Object based upon the comma seperated values
        public Team GetTeamFromString(String teamString)
        {
            try{
            Team returnTeam= new Team();

            //This will be the array of strings we populate to our new object.
            String[] StringArray = teamString.split(",");

            //Sanity check. I need four values, no more no less.
            if(StringArray.length != 4)
                throw new Exception("The Expected Number of values was 4 but found " + StringArray.length);

            //Populate the Team Object
            returnTeam.team_ID = StringArray[0];
            returnTeam.location = StringArray[1];
            returnTeam.name = StringArray[2];
            returnTeam.league = StringArray[3].charAt(0);
            return returnTeam;
            }
            catch (Exception e)
            {
                return null;
            }
        }

        //Get a list of all the teams from the DB.
        public ArrayList<Team> GetAllTeams(){
            ArrayList<Team> returnList = new ArrayList<Team>();
            try {
                BufferedReader br = new BufferedReader(new FileReader("teams.txt"));
                String line = br.readLine();
                Team temp = new Team();
                while (line != null) {
                    returnList.add(temp.GetTeamFromString(line));
                    line = br.readLine();
                }
                br.close();
                return returnList;
            }
            catch(Exception e) {
                return  null;
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
        
        Command cmd = null;
        while ((cmd = parser.fetchCommand()) != null) {
            
            boolean result=false;
            
            if (cmd.getCommand().equals("help")) {
                result = doHelp();

		/* You need to implement all the following commands */
            } else if (cmd.getCommand().equals("add_coach")) {

	    } else if (cmd.getCommand().equals("add_team")) {

		} else if (cmd.getCommand().equals("print_coaches")) {

	   	} else if (cmd.getCommand().equals("print_teams"))
            {
                //Get the list of Teams
                Team temp = new Team();
                ArrayList<Team> teams = temp.GetAllTeams();
                System.out.println("Printing List of Teams: " );

                if(teams.size() > 1)
                {
                    for(int i = 1; i< teams.size(); i++)
                    {
                        System.out.println(teams.get(i).team_ID + ", " + teams.get(i).location + ", " + teams.get(i).name + ", " + teams.get(i).league);
                    }
                }


		} else if (cmd.getCommand().equals("coaches_by_name")) {

		} else if (cmd.getCommand().equals("teams_by_city")) {

		} else if (cmd.getCommand().equals("load_coaches")) {

        } else if (cmd.getCommand().equals("load_teams")) {
		
		} else if (cmd.getCommand().equals("best_coach")) {

		}  else if (cmd.getCommand().equals("exit")) {
			System.out.println("Leaving the database, goodbye!");
			break;
		} else if (cmd.getCommand().equals("")) {
		} else {
			System.out.println("Invalid Command, try again!");
           	} 
            
	    if (result) {
                // ...
            }

            System.out.print("> "); 
        }        
    }
    
    private boolean doHelp() {
        System.out.println("add_coach ID SEASON FIRST_NAME LAST_NAME SEASON_WIN "); 
	System.out.println("          EASON_LOSS PLAYOFF_WIN PLAYOFF_LOSS TEAM - add new coach data");
        System.out.println("add_team ID LOCATION NAME LEAGUE - add a new team");
        System.out.println("print_coaches - print a listing of all coaches");
        System.out.println("print_teams - print a listing of all teams");
        System.out.println("coaches_by_name NAME - list info of coaches with the specified name");
        System.out.println("teams_by_city CITY - list the teams in the specified city");
	    System.out.println("load_coach FILENAME - bulk load of coach info from a file");
        System.out.println("load_team FILENAME - bulk load of team info from a file");
        System.out.println("best_coach SEASON - print the name of the coach with the most netwins in a specified season");
        System.out.println("search_coaches field=VALUE - print the name of the coach satisfying the specified conditions");
        System.out.println("delete_coaches field=VALUE - delete the coach satisfying the specified conditions");
		System.out.println("exit - quit the program");        
        return true;
    }
    
    /**
     * @param args
     */
}

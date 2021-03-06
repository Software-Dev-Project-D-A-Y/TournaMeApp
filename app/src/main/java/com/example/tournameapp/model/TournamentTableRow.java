package com.example.tournameapp.model;

import java.util.Comparator;

    public class TournamentTableRow implements Comparator<TournamentTableRow> {
        private Player player;
        private int wins, draws, loses, forGoals, againstGoals, goalsDifference, points;

        public TournamentTableRow(){}

        public TournamentTableRow(Player player) {
            this.player = player;
        }

        public void updateRow(int forGoals, int againstGoals) {
            this.forGoals += forGoals;
            this.againstGoals += againstGoals;
            this.goalsDifference = this.forGoals - this.againstGoals;
            if(forGoals > againstGoals) {
                wins++;
                points += 3;
            } else if(forGoals == againstGoals) {
                draws++;
                points++;
            } else {
                loses++;
            }
        }

        @Override
        public String toString() {
            return "TournamentTableRow{" +
                    "player=" + player.getUserName() +
                    ", points=" + points +
                    '}';
        }

        @Override
        public int compare(TournamentTableRow o1, TournamentTableRow o2) {
            int pointsComp = o2.getPoints() - o1.getPoints();
            int goalDiffComp = o2.getGoalsDifference() - o1.getGoalsDifference();
            int forGoalsComp = o2.getForGoals() - o1.getForGoals();
            if(pointsComp == 0) {

                if (goalDiffComp == 0){

                    if (forGoalsComp == 0) {
                        return o1.getPlayer().getUserName().compareTo(o2.getPlayer().getUserName());
                    } else {
                        return forGoalsComp;
                    }

                } else {
                    return goalDiffComp;
                }

            } else {
                return pointsComp;
            }
        }

        public Player getPlayer() {
            return player;
        }

        public void setPlayer(Player player) {
            this.player = player;
        }

        public int getWins() {
            return wins;
        }

        public void setWins(int wins) {
            this.wins = wins;
        }

        public int getDraws() {
            return draws;
        }

        public void setDraws(int draws) {
            this.draws = draws;
        }

        public int getLoses() {
            return loses;
        }

        public void setLoses(int loses) {
            this.loses = loses;
        }

        public int getForGoals() {
            return forGoals;
        }

        public void setForGoals(int forGoals) {
            this.forGoals = forGoals;
        }

        public int getAgainstGoals() {
            return againstGoals;
        }

        public void setAgainstGoals(int againstGoals) {
            this.againstGoals = againstGoals;
        }

        public int getGoalsDifference() {
            return goalsDifference;
        }

        public void setGoalsDifference(int goalsDifference) {
            this.goalsDifference = goalsDifference;
        }

        public int getPoints() {
            return points;
        }

        public void setPoints(int points) {
            this.points = points;
        }
    }

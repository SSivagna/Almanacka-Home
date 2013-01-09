package com.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.almanacka.planning.InputPlanning;
import com.almanacka.planning.OutputLesson;
import com.almanacka.planning.OutputPlanning;

public class InsertOutputLessonInDB
{
	static public void InsertOutputLesson(Connection connection, InputPlanning input, int p) throws SQLException
	{
		OutputPlanning a = OutputLesson.createOutputPlanning(input, p);
		
		for(int i = 0; i < a.getLessons().size(); i++ )
		{
			String sql = "INSERT INTO almanacka.lessonfortest (lessonId, idPlace, begDate, block, endDate, idIntensity , idPersonMonitor) VALUES (?, ?, ?, ?, ?, ?, ?)";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			try
			{
				OutputLesson lesson = a.getLessons().get(i);
			
	     		java.sql.Date sqlBegDate = new Date(lesson.getBegDate().getTime());
				java.sql.Date sqlEndDate = new Date(lesson.getEndDate().getTime());
				
			    preparedStatement.setInt(1,Integer.parseInt(lesson.getLessonId()));
				preparedStatement.setInt(2, Integer.parseInt(lesson.getPlaceWrapId()));
				preparedStatement.setDate(3, sqlBegDate );
				preparedStatement.setBoolean(4, false);
				preparedStatement.setDate(5, sqlEndDate);
				preparedStatement.setInt(6, Integer.parseInt(lesson.getMonitorId().get(0)));
				preparedStatement.setInt(7, Integer.parseInt(lesson.getIntensityId().get(0)));
				
				System.out.println(preparedStatement.toString());
				System.out.println(" ");
				/*	System.out.println(" DATES EN SQL ");
				System.out.println("sqlBegDate : " + sqlBegDate);
				System.out.println("sqlEndDate : " + sqlEndDate);
				 */	
				
				// 0 si pas inséré
				int rSet = preparedStatement.executeUpdate();
				System.out.println("Valeur du int rSet :" + rSet);
				System.out.println("   ");
			
				/*
				preparedStatement2.setInt(1, Integer.parseInt(lesson.getHostd().get(0)));
			
				preparedStatement2.setInt(2, i);
				*/
				
				// values ( outplanning.getLessons.getLessonsId et c avec iterator
				
				
		
				/*
				String sql2 = "INSERT INTO almanacka.lessonpersonhostfortest (idPersonHost) VALUES (?) WHERE lessonId = ? ";
				
				PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
				
				
				
				int rSet2 = preparedStatement2.executeUpdate();
				System.out.println("Valeur du int rSet2 :" + rSet2);
				System.out.println("   "); */
			}
			catch (Exception e)
			{
				System.out.println("Erreur lors de l'insertion de l'OuputLesson dans la base de données !!! ");
			}
			finally
			{
				if(preparedStatement != null)
				{
					preparedStatement.close();
				}
			}
		}
		
		Statement statement = connection.createStatement();
		ResultSet rSet = statement.executeQuery("SELECT lessonId from almanacka.lesson");
		
		List<Integer> lessonIdFromDB = new ArrayList<Integer>();
			
		while(rSet.next())
		{
		    int	lessonId = Integer.parseInt(rSet.getString("lessonId"));
		   
			lessonIdFromDB.add(lessonId);
		}
		System.out.println(lessonIdFromDB.toString());

		
		System.out.println("Insertion des outputlessons");

		for( int x = 0; x < lessonIdFromDB.size(); x++)
		{
			int variableLessonId = lessonIdFromDB.get(x);
			
		//	String sql2 = " INSERT INTO almanacka.lessonpersonhostfortest (idPersonHost) VALUES (?) WHERE lessonId = (?) " ;
			
			String sql2 = " UPDATE almanacka.lessonpersonhostfortest SET idPersonHost = ? WHERE lessonId = ? " ;
			
			PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
			
			preparedStatement2.setInt(1, Integer.parseInt(a.getLessons().get(x).getHostd().get(0)) );
			
			preparedStatement2.setInt(2, variableLessonId);
			
			System.out.println(preparedStatement2.toString());
			// INSERT INTO `almanacka`.`lessonpersonhostfortest` (`lessonId`, `idPersonHost`) VALUES ('1', '5');
			// UPDATE `almanacka`.`lessonpersonhostfortest` SET `idPersonHost`='50' WHERE `lessonId`='1';
			int rSet2 = preparedStatement2.executeUpdate();
			System.out.println("Valeur du int rSet2 :" + rSet2);
			System.out.println("   ");
		}
	}
}
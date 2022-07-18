var arrMenu = [
  {
    title: 'Menu',
    id: 'menuID',
    icon: '',
    items: [
	 {
	    name: 'Personal Information',
	    link: 'personalInformation.htm',
	    icon: 'ion-person-add'
	  },
      {
          name: 'Settings ',
          icon: 'fa fa-wrench fa-flip-horizontal',
          link: '#',
          items: [
            {
              title: 'Settings',
              icon: 'fa fa-wrench fa-flip-horizontal',
              items: [
                {
                  name: 'Create Classes',
                  link: 'AdminCreateClass.htm',
                  icon:'ion-easel'
                },
                {
                  name: 'Set Class Size',
                  link: 'AdminClassStrenth.htm',
                  icon: 'picol_group_half'
                },
                {
                  name: 'Create Section',
                  link: 'CreateSections.htm',
                  icon:'fa fa-th'
                },
                {
                  name: 'Set Periods',
                  link: 'SetPeriods.htm',
                  icon: 'ion-clock'
                },
                {
                  name: 'Set Fluency Cut Offs',
                  link: 'benchmarkCutOffMarks.htm',
                  icon: 'ion-scissors'
                }/*,
                {
                    name: 'Learning Indicator Rubric',
                    link: 'uploadIOLRubrics.htm',
                    icon: 'fa fa-hourglass-half'
                  }*/
                
              ]
            }
          ]
      },
      {
          name: 'Data Configurations',
          id: 'itemID',
          icon: 'fa fa-pencil-square-o',
          link: '#',
          items: [
            {
              title: 'Data Configurations',
              icon: 'fa fa-pencil-square-o',
              items: [
                {
                  name: 'Add School Information',
                  icon: 'fa fa-university',
                  link: 'AddSchoolInformation.htm'
                },
                {
                  name: 'Add Users',
                  icon: 'fa fa-user-plus',
                  link: 'AddUsers.htm'
                },
                {
                  name: 'Add Grades',
                  icon: 'fa fa-cubes',
                  link: 'AddGrades.htm'
                },
                {
                    name: 'Add Classes',
                    icon: 'fa fa-sitemap',
                    link: 'AdminAddClasses.htm'
                  },
                  {
                    name: 'Add School Days',
                    icon: 'fa fa-bus',
                    link: 'AddSchoolDays.htm'
                  },
                  {
                      name: 'Class Files',
                      link: 'displayClassFiles.htm?fileType=public',
                      icon: 'fa fa-files-o'
                   }
              ]
            }
          ]
        },
      {
          name: 'Registrations',
          id: 'itemID',
          icon: 'picol_group_full_add',
          link: '#',
          items: [
            {
              title: 'Registrations',
              icon: 'picol_group_full_add',
              items: [
                {
                  name: 'Student Registration',
                  icon: 'fa fa-user',
                  link: 'StudentRegistration.htm'
                },
                {
                  name: 'Teacher Registration',
                  icon: 'ion-person',
                  link: 'goToTeacherRegistration.htm'
                },
                {
                  name: 'Parent Registration',
                  icon: 'fi-torsos-male-female',
                  link: 'ParentRegistration.htm'
                }
              ]
            }
          ]
      },
      {
          name: 'Planners & Schedulers',
          icon: 'ion-ios-time',
          link: '#',
          items: [
            {
              title: 'Planners & Schedulers',
              icon: 'ion-ios-time',
              items: [
                {
                  name: 'Teacher Scheduler',
                  link: 'teacherScheduler.htm',
                  icon: 'picol_group_full'
                },
                {
                  name: 'Master Scheduler',
                  link: 'masterScheduler.htm',
                  icon: 'fa fa-calendar'
                },
                {
                  name: 'Automatic Scheduler',
                  link: 'AutomaticScheduler.htm',
                  icon: 'ion-ios-timer'
                }, 
                {
          	      name: 'Curriculum',
        	      link: 'curriculumPlan.htm',
        	      icon: 'picol_document_text_accept'
               },
              {
        	      name: 'Literacy Development',
        	      link: 'rti.htm',
        	      icon: 'fi-safety-cone'	  
              },
              {
                  name: 'Analytics Reports',
                  icon: 'fa fa-area-chart',
                  link: '#',
                  items: [
                    {
                      title: 'Analytics Reports',
                      icon: 'fa fa-area-chart',
                      items: [
    					{
                          name: 'STEAM',
                   	      link: 'stemAnalytics.htm',
                   	      icon: 'fa fa-puzzle-piece'	 
                        },
                        {
                          name: 'Goal Settings',
                 	      link: 'goalSettingsAnalytics.htm',
                 	      icon: 'fa fa-street-view'	 
                        }
        			  ]
        			}
        		 ]
               }
            ]
          }
         ]
       },
       {
        name: 'Student Panel',
        icon: 'fa fa-user',
        link: '#',
        items: [
          {
            title: 'Student Panel',
            icon: 'fa fa-user',
            items: [
			 {
			    name: 'Add Students To Class',
			    link: 'AddStudentsToClass.htm',
                icon:'fa fa-street-view'
			  },      
              {
                name: 'Student Reports',
                icon: 'ion-ios-paper',
                link: 'adminStudentReports.htm'
               },
              {
                name: 'Student Homework Reports',
                link: 'adminStudentHomeworkReports.htm',
                icon: 'fi-home'
              },
              {
                  name: 'View Daily Attendance',
                  link: 'viewDailyAttendance.htm',
                  icon: 'fi-torsos-all'
                }
            ]
          }
        ]
        },
        {
            name: 'Teacher Panel',
            icon: 'ion-person',
            link: '#',
            items: [
              {
                title: 'Teacher Panel',
                icon: 'ion-person',
                items: [
    			 {
    			    name: 'Teacher Lesson Content',
    			    link: 'displayTeacherClassFiles.htm?fileType=public&page=teacherLessonContent',
    			    icon: 'ion-ios-list'
    			  },      
                  {
                    name: 'Teacher Report Card',
                    icon: 'fa fa-tags',
                    link: 'TeacherSelfEvaluation.htm',
                    icon: 'ion-clipboard'
                   },
                  {
                    name: 'Teacher Self Evaluation',
                    link: 'AdminViewTeacherSelfEvaluation.htm',
                    icon: 'fa fa-tachometer'
                  }
                ]
              }
            ]
       }, 
       {
   	    name: 'California 5by5 Dashboard',
   	    link: 'california5by5Dashboard.htm',
   	    icon: 'fa fa-tachometer'
   	  },
 	  {
		    name: 'Log Out',
		    link: 'logOut.htm',
		    icon: 'fa fa-power-off'
	  }
    ]
  }
];
  -- VERSION 1

  -- -----------------------------------------------------
  -- Table User
  -- -----------------------------------------------------
  CREATE TABLE IF NOT EXISTS 'user' (
    '_id' INTEGER PRIMARY KEY AUTOINCREMENT,
    'name' TEXT NOT NULL,
    'mail' TEXT NOT NULL,
    'image' TEXT NOT NULL,
    'method_autentication' VARCHAR(45) NOT NULL);


  -- -----------------------------------------------------
  -- Table PreferenceUser
  -- -----------------------------------------------------
  CREATE TABLE IF NOT EXISTS 'preference_user' (
    '_id' INTEGER PRIMARY KEY AUTOINCREMENT,
    'fl_notifcation' BOOLEAN NOT NULL,
    'user_id' INT NOT NULL,
    CONSTRAINT 'fk_preference_user_user'
      FOREIGN KEY ('user_id')
      REFERENCES 'user' ('_id'));


  -- -----------------------------------------------------
  -- Table Event
  -- -----------------------------------------------------
  CREATE TABLE IF NOT EXISTS 'event' (
    '_id' INTEGER PRIMARY KEY AUTOINCREMENT,
    'name' TEXT NOT NULL,
    'description' TEXT NOT NULL,
    'banner' TEXT NULL,
    'dt_start' VARCHAR(100) NOT NULL,
    'dt_end' VARCHAR(100) NOT NULL,
    'contact_name' TEXT NOT NULL,
    'contact_phone' TEXT NOT NULL,
    'contact_mail' TEXT NOT NULL);


  -- -----------------------------------------------------
  -- Table Evaluation
  -- -----------------------------------------------------
  CREATE TABLE IF NOT EXISTS 'evaluation' (
    '_id' INTEGER PRIMARY KEY AUTOINCREMENT,
    'stars' DOUBLE NOT NULL,
    'comment' TEXT NULL,
    'dt_store' VARCHAR(100) NOT NULL,
    'dt_send' VARCHAR(100) NULL);


  -- -----------------------------------------------------
  -- Table Activity
  -- -----------------------------------------------------
  CREATE TABLE IF NOT EXISTS 'activity' (
    '_id' INTEGER PRIMARY KEY AUTOINCREMENT,
    'name' TEXT NOT NULL,
    'dt_start' VARCHAR(100) NOT NULL,
    'dt_end' VARCHAR(100) NOT NULL,
    'local_name' TEXT NOT NULL,
    'local_geolocation' TEXT NULL,
    'event_id' INT NOT NULL,
    'evaluation_id' INT NOT NULL,
    CONSTRAINT 'fk_activity_event'
      FOREIGN KEY ('event_id')
      REFERENCES 'event' ('_id'),
    CONSTRAINT 'fk_activity_evaluation'
      FOREIGN KEY ('evaluation_id')
      REFERENCES 'evaluation' ('_id'));


  -- -----------------------------------------------------
  -- Table TypeAttachment
  -- -----------------------------------------------------
  CREATE TABLE IF NOT EXISTS 'type_attachment' (
    '_id' INTEGER PRIMARY KEY AUTOINCREMENT,
    'name' TEXT NOT NULL,
    'image' TEXT NULL);


  -- -----------------------------------------------------
  -- Table Attachment
  -- -----------------------------------------------------
  CREATE TABLE IF NOT EXISTS 'attachment' (
    '_id' INTEGER PRIMARY KEY AUTOINCREMENT,
    'name' TEXT NOT NULL,
    'local' TEXT NOT NULL,
    'activity_id' INT NOT NULL,
    'type_attachment_id' INT NOT NULL,
    CONSTRAINT 'fk_attachment_activity'
      FOREIGN KEY ('activity_id')
      REFERENCES 'activity' ('_id')    
    CONSTRAINT 'fk_attachment_type_attachment'
      FOREIGN KEY ('type_attachment_id')
      REFERENCES 'type_attachment' ('_id'));


  -- -----------------------------------------------------
  -- Table Message
  -- -----------------------------------------------------
  CREATE TABLE IF NOT EXISTS 'message' (
    '_id' INTEGER PRIMARY KEY AUTOINCREMENT,
    'content' TEXT NOT NULL,
    'dt_store' VARCHAR(100) NOT NULL,
    'dt_send' VARCHAR(100) NOT NULL,
    'user_id' INT NOT NULL,
    'activity_id' INT NOT NULL,
    CONSTRAINT 'fk_message_user'
      FOREIGN KEY ('user_id')
      REFERENCES 'user' ('_id')
    CONSTRAINT 'fk_message_activity'
      FOREIGN KEY ('activity_id')
      REFERENCES 'activity' ('_id'));
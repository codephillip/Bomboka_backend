package models.User;

import org.bson.types.ObjectId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

/**
 * Created by Ahereza on 11/25/16.
 */
public class Role {
    @MongoObjectId
    private ObjectId _id;
}

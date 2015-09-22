package org.baeldung.gridfs;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.baeldung.config.MongoConfig;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFSDBFile;

@ContextConfiguration(classes = MongoConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class GridFSTest {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
       
    @Autowired
    private GridFsTemplate gridFsTemplate;
     
    @After
    public void tearDown() {
        List<GridFSDBFile> fileList = gridFsTemplate.find(null);
        for(GridFSDBFile file: fileList) {
            gridFsTemplate.delete(new Query(Criteria.where("filename").is(file.getFilename())));
        }
    }

    @Test
    public void whenStoringFileWithMetadata_thenFileAndMetadataAreStored() {
        DBObject metaData = new BasicDBObject();
        metaData.put("key", "value");
        InputStream inputStream = null;
        String id = "";

        try {
            inputStream = new FileInputStream("src/main/resources/test.png");
            id = gridFsTemplate.store(inputStream, "test.png", "image/png", metaData).getId().toString();
        } catch (FileNotFoundException ex) {
            logger.error("", ex);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    logger.error("", ex);
                }
            }
        }

        assertNotNull(id);
    }

    @Test
    public void givenFileWithMetadataExist_whenFindingFileById_thenFileWithMetadataIsFound() {
        DBObject metaData = new BasicDBObject();
        metaData.put("key", "value");
        InputStream inputStream = null;
        String id = "";

        try {
            inputStream = new FileInputStream("src/main/resources/test.png");
            id = gridFsTemplate.store(inputStream, "test.png", "image/png", metaData).getId().toString();
        } catch (FileNotFoundException ex) {
            logger.error("", ex);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    logger.error("", ex);
                }
            }
        }
        
        GridFSDBFile gridFSDBFile = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
             
        assertNotNull(gridFSDBFile);
        assertThat(gridFSDBFile.getFilename(), is("test.png"));
        assertThat(gridFSDBFile.getMetaData().get("key"), is("value"));
    }

    @Test
    public void givenMetadataAndFilesExist_whenFindingAllFiles_thenFilesWithMetadataAreFound() {
        DBObject metaData = new BasicDBObject();
        metaData.put("key", "value");
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream("src/main/resources/test.png");
            gridFsTemplate.store(inputStream, "test.png", "image/png", metaData);
            gridFsTemplate.store(inputStream, "test.png", "image/png", metaData);
        } catch (FileNotFoundException ex) {
            logger.error("", ex);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    logger.error("", ex);
                }
            }
        }
        
        List<GridFSDBFile> gridFSDBFiles = gridFsTemplate.find(null);
        
        assertNotNull(gridFSDBFiles);
        assertThat(gridFSDBFiles.size(), is(2));
    }
    
    
    @Test
    public void givenFileWithMetadataExist_whenDeletingFileById_thenFileWithMetadataIsDeleted() {
        DBObject metaData = new BasicDBObject();
        metaData.put("key", "value");
        InputStream inputStream = null;
        String id = "";

        try {
            inputStream = new FileInputStream("src/main/resources/test.png");
            id = gridFsTemplate.store(inputStream, "test.png", "image/png", metaData).getId().toString();
        } catch (FileNotFoundException ex) {
            logger.error("", ex);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    logger.error("", ex);
                }
            }
        }
        
        gridFsTemplate.delete(new Query(Criteria.where("_id").is(id)));
             
        assertThat(gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id))), is(nullValue()));
    }
    
    @Test
    public void givenFileWithMetadataExist_whenGettingFileByResource_thenFileWithMetadataIsGotten() {
        DBObject metaData = new BasicDBObject();
        metaData.put("key", "value");
        InputStream inputStream = null;
        String id = "";

        try {
            inputStream = new FileInputStream("src/main/resources/test.png");
            id = gridFsTemplate.store(inputStream, "test.png", "image/png", metaData).getId().toString();
        } catch (FileNotFoundException ex) {
            logger.error("", ex);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ex) {
                    logger.error("", ex);
                }
            }
        }
        
        GridFsResource[] gridFsResource = gridFsTemplate.getResources("test*");
             
        assertNotNull(gridFsResource);
        assertThat(gridFsResource[0].getFilename(), is("test.png"));
    }
}

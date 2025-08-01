package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

@Service
public class ImageServiceImpl implements ImageService {
    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void saveImageFile(Long recipeId, MultipartFile file) {
        try{
            Recipe recipe = recipeRepository.findById(recipeId).get();

            Byte[] byteObjects = new Byte[file.getBytes().length];

            int i = 0;
            for (byte b : file.getBytes()) {
                byteObjects[i++] = b; // Autoboxing from byte to Byte
            }

            recipe.setImage(byteObjects);

            recipeRepository.save(recipe);
        } catch (Exception e) {
            // Handle the exception, e.g., log it or rethrow it
            e.printStackTrace();
            throw new RuntimeException("Failed to save image file", e);
        }
    }
}

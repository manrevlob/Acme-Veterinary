package controllers.actor;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.MessageFolderService;
import controllers.AbstractController;
import domain.MessageFolder;

@Controller
@RequestMapping("/messageFolder/actor")
public class MessageFolderActorController extends AbstractController {
	// Services ---------------------------------------------------------------

	@Autowired
	private MessageFolderService messageFolderService;

	// Constructors -----------------------------------------------------------

	public MessageFolderActorController() {
		super();
	}

	// Listing ----------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<MessageFolder> messageFolders;

		messageFolders = messageFolderService.findByActor();

		result = new ModelAndView("messageFolder/list");
		result.addObject("requestURI", "messageFolder/actor/list.do");
		result.addObject("messageFolders", messageFolders);

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		MessageFolder messageFolder;

		messageFolder = messageFolderService.create();

		result = new ModelAndView("messageFolder/create");
		result.addObject("messageFolder", messageFolder);
		result.addObject("requestURI", "messageFolder/actor/create.do");

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid MessageFolder messageFolder,
			BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("messageFolder/create");
		} else {
			try {
				messageFolder = messageFolderService.save(messageFolder);
				messageFolderService.assignFolderToActor(messageFolder);

				result = new ModelAndView(
						"redirect:/messageFolder/actor/list.do");
			} catch (Throwable oops) {
				result = new ModelAndView("messageFolder/create");
				result.addObject("messageFolder", messageFolder);
				result.addObject("requestURI", "messageFolder/actor/create.do");
				result.addObject("message", "messageFolder.commit.error");
			}
		}

		return result;
	}

	// Edit ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam int messageFolderId) {
		ModelAndView result;
		MessageFolder messageFolder;
		if (messageFolderService.isOwner(messageFolderId)) {
			messageFolder = messageFolderService.findOne(messageFolderId);

			result = new ModelAndView("messageFolder/create");
			result.addObject("messageFolder", messageFolder);
			result.addObject("requestURI", "messageFolder/actor/edit.do");
			result.addObject("edit", true);
		} else {
			result = new ModelAndView("misc/403");
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView edit(@Valid MessageFolder messageFolder,
			BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			result = new ModelAndView("messageFolder/create");
			result.addObject("messageFolder", messageFolder);
			result.addObject("edit", true);
		} else {
			try {
				messageFolderService.save(messageFolder);
				result = new ModelAndView(
						"redirect:/messageFolder/actor/list.do");
			} catch (Throwable oops) {
				result = new ModelAndView("messageFolder/create");
				result.addObject("messageFolder", messageFolder);
				result.addObject("requestURI", "messageFolder/actor/edit.do");
				result.addObject("message", "messageFolder.commit.error");
				result.addObject("edit", true);
			}
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(@Valid MessageFolder messageFolder,
			BindingResult binding) {
		ModelAndView result;

		try {

			messageFolderService.deleteOfActor(messageFolder);

			result = new ModelAndView("redirect:/messageFolder/actor/list.do");
		} catch (Throwable oops) {
			result = new ModelAndView("redirect:/messageFolder/actor/list.do");
			result.addObject("message", "messageFolder.commit.error");
			result.addObject("edit", true);
		}

		return result;
	}

	// Details ----------------------------------------------------------------

	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public ModelAndView details(@RequestParam int messageFolderId) {
		ModelAndView result;
		MessageFolder messageFolder;

		if (messageFolderService.isOwner(messageFolderId)) {
			messageFolder = messageFolderService.findOne(messageFolderId);

			result = new ModelAndView("messageFolder/details");
			result.addObject("messageFolder", messageFolder);
		} else {
			result = new ModelAndView("misc/403");
		}

		return result;
	}

}
